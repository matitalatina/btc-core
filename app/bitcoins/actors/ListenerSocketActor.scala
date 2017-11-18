package bitcoins.actors

import javax.inject.{Inject, Named}

import akka.actor.{Actor, ActorRef}
import bitcoins.Formatters._
import bitcoins.actors.ListenerSocketActor.SendToClient
import bitcoins.providers.RateHistoryProvider
import bitcoins.stats.RateStatsCalculator
import bitcoins.viewmodels.RateStatsResponse
import com.google.inject.assistedinject.Assisted
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext

object ListenerSocketActor {

  trait Factory {
    def apply(out: ActorRef, currencyCode: String): Actor
  }

  sealed trait Message

  case class SendToClient(currencyCode: String) extends Message

}

class ListenerSocketActor @Inject()(
                                     @Named(UpdateRoomActor.name) updateRoom: ActorRef,
                                     rateHistoryProvider: RateHistoryProvider,
                                     rateStatsCalculator: RateStatsCalculator,
                                     @Assisted out: ActorRef,
                                     @Assisted currencyCode: String,
                                   )(implicit ec: ExecutionContext) extends Actor {
  updateRoom ! UpdateRoomActor.RegisterSocket(self)
  sendRateStatResponse

  override def receive = {
    case SendToClient(code) if code == currencyCode => sendRateStatResponse
  }

  override def postStop() = {
    updateRoom ! UpdateRoomActor.UnRegisterSocket(self)
  }

  private def sendRateStatResponse = for {
    history <- rateHistoryProvider.get(currencyCode)
  } yield
    history.lastOption.foreach(h => out ! Json.toJson(RateStatsResponse(h, rateStatsCalculator.stats(history))))
}
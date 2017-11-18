package bitcoins.actors

import akka.actor.{Actor, ActorRef, Props}
import bitcoins.Formatters._
import bitcoins.actors.ListenerSocketActor.SendToClient
import bitcoins.providers.RateHistoryProvider
import bitcoins.stats.RateStatsCalculator
import bitcoins.viewmodels.RateStatsResponse
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global

object ListenerSocketActor {
  def props(currencyCode: String,
            updateRoom: ActorRef,
            rateHistoryProvider: RateHistoryProvider,
            rateStatsCalculator: RateStatsCalculator)(out: ActorRef): Props = {

    Props(new ListenerSocketActor(
      out,
      currencyCode,
      updateRoom,
      rateHistoryProvider,
      rateStatsCalculator))
  }

  sealed trait Message

  case class SendToClient(currencyCode: String) extends Message

}

class ListenerSocketActor(
                           out: ActorRef,
                           currencyCode: String,
                           updateRoom: ActorRef,
                           rateHistoryProvider: RateHistoryProvider,
                           rateStatsCalculator: RateStatsCalculator) extends Actor {
  updateRoom ! UpdateRoomActor.RegisterSocket(self)

  override def receive = {
    case SendToClient(code) if code == currencyCode => for {
      history <- rateHistoryProvider.get(currencyCode)
    } yield
      history.lastOption.foreach(h => out ! Json.toJson(RateStatsResponse(h, rateStatsCalculator.stats(history))))
  }

  override def postStop() = {
    updateRoom ! UpdateRoomActor.UnRegisterSocket(self)
  }
}
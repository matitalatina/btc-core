package bitcoins.providers

import javax.inject.{Inject, _}

import akka.actor.ActorRef
import bitcoins.actors.UpdateRoomActor
import bitcoins.models.RateHistory
import bitcoins.viewmodels.Rate

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MemoryRateHistoryProvider @Inject()(
                                           @Named(UpdateRoomActor.name) updateRoom: ActorRef,
                                         )(implicit ec: ExecutionContext) extends RateHistoryProvider {
  override def get(code: String, limit: Option[Int]) = Future {
    val history = repo.getOrElse(code, Seq())
    limit.map(history.takeRight).getOrElse(history)
  }

  override def saveRates(rates: Seq[Rate]) = Future {
    val now = System.currentTimeMillis()
    val ratesToUpdate = rates.filter(r => repo.get(r.code).flatMap(_.lastOption).forall(c => c.rate != r.rate))
    ratesToUpdate
      .foreach(rateToUpdate => {
        repo += (rateToUpdate.code -> (repo.getOrElse(rateToUpdate.code, Seq()) :+ RateHistory(rateToUpdate.rate, now)))
        updateRoom ! UpdateRoomActor.BroadcastUpdate(rateToUpdate.code)
      })
  }

  var repo: Map[String, Seq[RateHistory]] = Map()
}

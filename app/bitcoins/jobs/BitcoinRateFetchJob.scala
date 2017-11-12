package bitcoins.jobs

import javax.inject.Inject

import akka.actor.ActorSystem
import bitcoins.workers.PopulateWorker

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._


class BitcoinRateFetchJob @Inject()(
                                     actorSystem: ActorSystem,
                                     populateWorker: PopulateWorker
                                   )(implicit executionContext: ExecutionContext) {
  actorSystem.scheduler.schedule(initialDelay = 0.microseconds, interval = 5.seconds) {
    populateWorker.populateBitcoinRates()
  }
}

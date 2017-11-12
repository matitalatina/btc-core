package bitcoins.jobs

import javax.inject.Inject

import akka.actor.ActorSystem
import bitcoins.providers.RateHistoryProvider
import bitcoins.services.BitcoinRatesService

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._


class BitcoinRateFetchJob @Inject()(
                                     actorSystem: ActorSystem,
                                     bitcoinRatesService: BitcoinRatesService,
                                     rateHistoryProvider: RateHistoryProvider,
                                   )(implicit executionContext: ExecutionContext) {
  actorSystem.scheduler.schedule(initialDelay = 0.microseconds, interval = 5.seconds) {
    for {
      rates <- bitcoinRatesService.fetch()
      _ <- rateHistoryProvider.saveRates(rates.getOrElse(Seq()))
    } yield ()
  }
}

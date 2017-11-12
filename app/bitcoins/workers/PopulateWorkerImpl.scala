package bitcoins.workers

import javax.inject.Inject

import akka.actor.ActorSystem
import bitcoins.models.Currency
import bitcoins.providers.{CurrencyProvider, RateHistoryProvider}
import bitcoins.services.BitcoinRatesService

import scala.concurrent.{ExecutionContext, Future}

class PopulateWorkerImpl @Inject()(
                                    actorSystem: ActorSystem,
                                    bitcoinRatesService: BitcoinRatesService,
                                    rateHistoryProvider: RateHistoryProvider,
                                    currencyProvider: CurrencyProvider,
                                  )(implicit executionContext: ExecutionContext) extends PopulateWorker {
  override def populateBitcoinRates(): Future[Unit] = for {
    ratesOpt <- bitcoinRatesService.fetch()
    rates = ratesOpt.getOrElse(Seq())
    _ <- currencyProvider.save(rates.map(Currency.fromRate).toSet)
    _ <- rateHistoryProvider.saveRates(rates)
  } yield ()
}

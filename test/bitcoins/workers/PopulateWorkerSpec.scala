package bitcoins.workers

import bitcoins.Fixtures
import bitcoins.mocks.MockBitcoinRatesService
import bitcoins.providers.{CurrencyProvider, RateHistoryProvider}
import bitcoins.services.BitcoinRatesService
import org.scalatestplus.play.PlaySpec
import play.api.Application
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.Helpers._


class PopulateWorkerSpec extends PlaySpec {

  private def application: Application = {
    new GuiceApplicationBuilder()
      .overrides(bind[BitcoinRatesService].to[MockBitcoinRatesService])
      .build()
  }

  "PopulateWorker" should {
    "save both currencies and history" in {
      val worker = application.injector.instanceOf[PopulateWorker]
      await(worker.populateBitcoinRates())
      val currencyProvider = application.injector.instanceOf[CurrencyProvider]
      val savedCurrencies = await(currencyProvider.getAll)
      savedCurrencies mustEqual Set(Fixtures.currencyEur, Fixtures.currencyBtc)
      val historyProvider = application.injector.instanceOf[RateHistoryProvider]
      await(historyProvider.get(Fixtures.codeBtc)) must not be empty
      await(historyProvider.get(Fixtures.codeEur)) must not be empty
    }
  }

}

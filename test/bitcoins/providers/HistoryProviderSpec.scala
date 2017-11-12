package bitcoins.providers

import bitcoins.Fixtures
import bitcoins.viewmodels.Rate
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting
import play.api.test.Helpers._

class HistoryProviderSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  "HistoryProviderSpec" should {
    "save rates" in {
      val now = System.currentTimeMillis()
      val currencyProvider = inject[RateHistoryProvider]
      await(currencyProvider.saveRates(Seq(Rate(Fixtures.codeBtc, Fixtures.nameBtc, 0.12))))
      await(currencyProvider.saveRates(Seq(Rate(Fixtures.codeBtc, Fixtures.nameBtc, 0.13))))
      val rateHistory = await(currencyProvider.get(Fixtures.codeBtc))
      rateHistory.map(_.rate) mustEqual Seq(0.12, 0.13)
      rateHistory.forall(_.stamp >= now) mustBe true
    }

    "save only one time same rate" in {
      val currencyProvider = inject[RateHistoryProvider]
      await(currencyProvider.saveRates(Seq(Rate(Fixtures.codeBtc, Fixtures.nameBtc, 0.12))))
      await(currencyProvider.saveRates(Seq(Rate(Fixtures.codeBtc, Fixtures.nameBtc, 0.12))))
      val rateHistory = await(currencyProvider.get(Fixtures.codeBtc))
      rateHistory.map(_.rate) mustEqual Seq(0.12)
    }

    "get rates" in {
      val currencyProvider = inject[RateHistoryProvider]
      await(currencyProvider.saveRates(Seq(Rate(Fixtures.codeBtc, Fixtures.nameBtc, 0.12))))
      await(currencyProvider.saveRates(Seq(Rate(Fixtures.codeBtc, Fixtures.nameBtc, 0.13))))
      val rateHistory = await(currencyProvider.get(Fixtures.codeBtc))
      rateHistory.size mustBe 2
      val notExistHistory = await(currencyProvider.get(Fixtures.codeEur))
      notExistHistory mustBe empty
    }
  }

}

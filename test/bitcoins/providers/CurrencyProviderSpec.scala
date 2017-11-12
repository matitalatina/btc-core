package bitcoins.providers

import bitcoins.Fixtures
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test.Injecting

import scala.concurrent.ExecutionContext.Implicits.global

class CurrencyProviderSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  "MemoryCurrencyProvider" should {
    "save all currencies provided" in {
      val currencyProvider = inject[CurrencyProvider]
      val savedCurrencies = await(currencyProvider.save(Set(Fixtures.currencyBtc, Fixtures.currencyEur)))
      savedCurrencies.size mustEqual 2
      savedCurrencies must contain(Fixtures.currencyBtc)
      savedCurrencies must contain(Fixtures.currencyEur)
    }

    "get single currency" in {
      val currencyBtc = Fixtures.currencyBtc
      val currencyProvider = inject[CurrencyProvider]
      await(currencyProvider.save(Set(currencyBtc, Fixtures.currencyEur)))
      val btc = await(currencyProvider.get(Fixtures.currencyBtc.code))
      btc mustEqual Some(currencyBtc)
      val notSavedCurrency = await(currencyProvider.get("PRR"))
      notSavedCurrency mustBe None
    }

    "get all currencies" in {
      val currencyProvider = inject[CurrencyProvider]
      val allCurrencies = Set(Fixtures.currencyBtc, Fixtures.currencyEur)
      await(currencyProvider.save(Set(Fixtures.currencyBtc))
        .flatMap(_ => currencyProvider.save(Set(Fixtures.currencyEur))))
      val savedCurrencies = await(currencyProvider.getAll)
      savedCurrencies mustEqual allCurrencies
    }
  }

}

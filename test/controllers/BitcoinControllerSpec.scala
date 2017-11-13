package controllers

import bitcoins.Fixtures
import bitcoins.providers.CurrencyProvider
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test.{Injecting, _}

class BitcoinControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  "Currencies GET" should {
    "return all currencies" in {
      val currencyProvider = inject[CurrencyProvider]
      await(currencyProvider.save(Set(Fixtures.currencyBtc, Fixtures.currencyEur)))
      val controller = inject[BitcoinController]
      val home = controller.currencies().apply(FakeRequest(GET, "/bitcoins/currencies/"))

      status(home) mustBe OK
      contentAsString(home) mustBe """[{"code":"BTC","name":"Bitcoin"},{"code":"EUR","name":"Euro"}]"""
      contentType(home) mustBe Some("application/json")
    }
  }

  "RateStats GET" should {
    "return 404 if no currency found" in {
      val controller = inject[BitcoinController]
      val rate = controller.rateStats("WHAT").apply(FakeRequest(GET, "/bitcoins/rates/WHAT/"))
      status(rate) mustBe NOT_FOUND
    }
  }
}

package controllers

import bitcoins.Fixtures
import bitcoins.Formatters._
import bitcoins.models.RateHistory
import bitcoins.providers.{CurrencyProvider, RateHistoryProvider}
import bitcoins.viewmodels.{Rate, RateStatsResponse}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test.{Injecting, _}

import scala.concurrent.ExecutionContext.Implicits.global

class BitcoinControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  val MAX_RATES_COUNT = 100

  def loadCurrencySample = {
    val currencyProvider = inject[CurrencyProvider]
    val rateHistoryProvider = inject[RateHistoryProvider]

    for {
      _ <- currencyProvider.save(Set(Fixtures.currencyEur))
      _ <- rateHistoryProvider.saveRates((1 to MAX_RATES_COUNT)
        .map(r => Rate(Fixtures.codeEur, Fixtures.nameEur, r / 100.0)))
    } yield ()

  }

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

  /*"RateStats GET" should {
    "return 404 if no currency found" in {
      val controller = inject[BitcoinController]
      val rate = controller.rateHistory("WHAT").apply(FakeRequest(GET, "/bitcoins/rates/WHAT/"))
      status(rate) mustBe NOT_FOUND
    }
  }*/

  "RateHistory GET" should {
    "return all rates of a currency" in {
      await(loadCurrencySample)
      val controller = inject[BitcoinController]
      val rate = controller.rateHistory(Fixtures.codeEur)
        .apply(FakeRequest(GET, s"/bitcoins/rates/${Fixtures.codeEur}/"))
      status(rate) mustBe OK
      val rateStats = contentAsJson(rate).validate[Seq[RateHistory]].get
      rateStats.size mustBe MAX_RATES_COUNT
    }
    "handle limit query param" in {
      await(loadCurrencySample)
      val LIMIT = 5
      val controller = inject[BitcoinController]
      val rate = controller.rateHistory(Fixtures.codeEur, Some(LIMIT))
        .apply(FakeRequest(GET, s"/bitcoins/rates/${Fixtures.codeEur}/?limit=$LIMIT"))
      status(rate) mustBe OK
      val rateStats = contentAsJson(rate).validate[Seq[RateHistory]].get
      rateStats.size mustBe LIMIT
    }
  }
}

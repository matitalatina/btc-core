package bitcoins.services

import bitcoins.viewmodels.Rate
import mockws.MockWS
import mockws.MockWSHelpers._
import org.scalatestplus.play.PlaySpec
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSClient
import play.api.mvc.Results._
import play.api.test.Helpers._

class BitcoinRatesServicesSpec extends PlaySpec {
  private def rateService(mockWs: MockWS): BitcoinRatesService = {
    val application = new GuiceApplicationBuilder()
      .overrides(bind[WSClient].toInstance(mockWs))
      .build()

    application.injector.instanceOf[BitcoinRatesService]
  }

  "BitcointRatesServicesImpl" should {
    "return rates" in {
      val ws = MockWS.apply({
        case (GET, "https://bitpay.com/api/rates/") => Action {
          Ok("""[{"code":"BTC","name":"Bitcoin","rate":1},{"code":"USD","name":"US Dollar","rate":6450},{"code":"EUR","name":"Eurozone Euro","rate":5529.456}]""")
        }
      })
      val service = rateService(ws)
      val rates = await(service.fetch())
      rates mustEqual Option(Seq(
        Rate("BTC", "Bitcoin", 1),
        Rate("USD", "US Dollar", 6450),
        Rate("EUR", "Eurozone Euro", 5529.456),
      ))
    }

    "return None if any error" in {
      val ws = MockWS.apply({
        case (GET, "https://bitpay.com/api/rates/") => Action(BadGateway("Error"))
      })
      await(rateService(ws).fetch()) mustBe None
    }
  }
}

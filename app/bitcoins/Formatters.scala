package bitcoins

import bitcoins.models.Currency
import bitcoins.viewmodels.Rate
import play.api.libs.json.{Format, Json, OFormat}

object Formatters {
  implicit val bitcoinRate: OFormat[Rate] = Json.format[Rate]
  implicit val currencyF: OFormat[Currency] = Json.format[Currency]
}

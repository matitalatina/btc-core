package bitcoins

import bitcoins.viewmodels.Rate
import play.api.libs.json.{Json, OFormat}

object Formatters {
  implicit val bitcoinRate: OFormat[Rate] = Json.format[Rate]
}

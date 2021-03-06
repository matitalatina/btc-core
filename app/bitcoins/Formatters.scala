package bitcoins

import bitcoins.models.{Currency, RateHistory}
import bitcoins.viewmodels.{Rate, RateStatsResponse, Stats}
import play.api.libs.json.{Json, OFormat}

object Formatters {
  implicit val bitcoinRate: OFormat[Rate] = Json.format[Rate]
  implicit val rateHistoryF: OFormat[RateHistory] = Json.format[RateHistory]
  implicit val currencyF: OFormat[Currency] = Json.format[Currency]
  implicit val statsResponseF: OFormat[Stats] = Json.format[Stats]
  implicit val rateStatsResponseF: OFormat[RateStatsResponse] = Json.format[RateStatsResponse]
}

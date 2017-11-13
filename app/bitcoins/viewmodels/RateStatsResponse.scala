package bitcoins.viewmodels

import bitcoins.models.Currency

case class RateStatsResponse(currency: Currency, history: Option[Seq[RateHistory]])

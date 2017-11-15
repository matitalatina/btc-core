package bitcoins.viewmodels

import bitcoins.models.{Currency, RateHistory}

case class RateStatsResponse(currency: Currency, history: Option[Seq[RateHistory]])

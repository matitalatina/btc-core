package bitcoins.viewmodels

import bitcoins.models.RateHistory

case class RateStatsResponse(rate: RateHistory, stats: Stats)

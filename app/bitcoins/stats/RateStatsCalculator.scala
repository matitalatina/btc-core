package bitcoins.stats

import bitcoins.viewmodels.RateHistory

trait RateStatsCalculator {
  def weightedAverage(history: Seq[RateHistory]): BigDecimal
  def max(history: Seq[RateHistory]): BigDecimal
  def min(history: Seq[RateHistory]): BigDecimal
  def variance(history: Seq[RateHistory]): BigDecimal
}

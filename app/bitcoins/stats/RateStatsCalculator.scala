package bitcoins.stats

import bitcoins.models.RateHistory
import bitcoins.viewmodels.Stats

trait RateStatsCalculator {
  def weightedAverage(history: Seq[RateHistory]): BigDecimal
  def max(history: Seq[RateHistory]): BigDecimal
  def min(history: Seq[RateHistory]): BigDecimal
  def variance(history: Seq[RateHistory]): BigDecimal
  def stats(history: Seq[RateHistory]): Stats
}

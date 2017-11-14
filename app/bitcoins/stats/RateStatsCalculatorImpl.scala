package bitcoins.stats

import javax.inject.Singleton

import bitcoins.viewmodels.RateHistory

@Singleton
class RateStatsCalculatorImpl extends RateStatsCalculator {
  def weightedAverage(history: Seq[RateHistory]): BigDecimal = {
    val orderedHistory = history.sortBy(_.stamp)
    val lowOpt = orderedHistory.headOption
    val highOpt = orderedHistory.lastOption
    val avg = for {
      low <- lowOpt
      high <- highOpt
    } yield history.zip(history.tail :+ history.last)
      .map({ case (h1, h2) => h1.rate * (h2.stamp - h1.stamp) }).sum / (high.stamp - low.stamp)
    avg.getOrElse(0)
  }

  def max(history: Seq[RateHistory]): BigDecimal = history.map(_.rate).max

  def min(history: Seq[RateHistory]): BigDecimal = history.map(_.rate).min

  def variance(history: Seq[RateHistory]): BigDecimal = history match {
    case Seq() => 0
    case _ =>
      val avg = weightedAverage(history)
      history.map(h => Math.pow((h.rate - avg).toDouble, 2)).sum / history.size
  }
}

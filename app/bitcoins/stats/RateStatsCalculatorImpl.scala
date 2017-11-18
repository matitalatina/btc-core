package bitcoins.stats

import javax.inject.Singleton

import bitcoins.models.RateHistory
import bitcoins.viewmodels.Stats

@Singleton
class RateStatsCalculatorImpl extends RateStatsCalculator {
  private def withWeights(history: Seq[RateHistory]): Seq[(RateHistory, Double)] = {
    val orderedHistory = history.sortBy(_.stamp)
    val lowOpt = orderedHistory.headOption
    val highOpt = orderedHistory.lastOption
    val weighted = for {
      low <- lowOpt
      high <- highOpt
      span = (high.stamp - low.stamp).toDouble
    } yield history.zip(history.tail :+ history.last)
      .map({ case (h1, h2) => h1 -> (if (span != 0) (h2.stamp - h1.stamp).toDouble / span else 0) })
    weighted.getOrElse(Seq.empty)
  }

  def weightedAverage(history: Seq[RateHistory]): BigDecimal = withWeights(history)
    .map({ case (h, weight) => h.rate * weight })
    .sum


  def max(history: Seq[RateHistory]): BigDecimal = history match {
    case Seq() => 0
    case _ => history.map(_.rate).max
  }

  def min(history: Seq[RateHistory]): BigDecimal = history match {
    case Seq() => 0
    case _ => history.map(_.rate).min
  }

  def variance(history: Seq[RateHistory]): BigDecimal = {
    val avg = weightedAverage(history)
    withWeights(history)
      .map({ case (h, weight) => weight * Math.pow((h.rate - avg).toDouble, 2) })
      .sum
  }

  override def stats(history: Seq[RateHistory]) = Stats(
    min = min(history),
    max = max(history),
    avg = weightedAverage(history),
    variance = variance(history),
  )
}

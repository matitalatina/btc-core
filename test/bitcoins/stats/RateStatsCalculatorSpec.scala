package bitcoins.stats

import bitcoins.models.RateHistory
import bitcoins.viewmodels.Stats
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

class RateStatsCalculatorSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  val filledHistory: Seq[RateHistory] = Seq(
    (0.4, 1),
    (0.6, 2),
    (3.2, 4),
    (52.1, 8),
    (12.3, 9),
    (23.5, 10),
  ).map({ case (rate, stamp) => RateHistory(rate, stamp) })
  val historyMin = 0.4
  val historyMax = 52.1
  val historyAvg = BigDecimal("8.75555555555555468")
  val historyVariance = 246.40024691358025

  def calculator: RateStatsCalculator = inject[RateStatsCalculator]

  "RateStatsCalculator" should {
    "return 0 for all stats with empty history" in {
      val history = Seq.empty[RateHistory]
      val calculations = Seq(
        calculator.max(_),
        calculator.min(_),
        calculator.variance(_),
        calculator.weightedAverage(_),
      )
      calculations.foreach(f => f(history) mustEqual 0)
    }
    "calculate max" in {
      calculator.max(filledHistory) mustEqual historyMax
    }
    "calculate min" in {
      calculator.min(filledHistory) mustEqual historyMin
    }
    "calculate avg" in {
      calculator.weightedAverage(filledHistory) mustEqual historyAvg
    }
    "calculate variance" in {
      calculator.variance(filledHistory) mustEqual historyVariance
    }
    "calculate stats" in {
      calculator.stats(filledHistory) mustEqual Stats(
        historyMin,
        historyMax,
        historyAvg,
        historyVariance,
      )
    }
  }
}

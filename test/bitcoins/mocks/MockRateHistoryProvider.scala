package bitcoins.mocks

import bitcoins.providers.RateHistoryProvider
import bitcoins.viewmodels.{Rate, RateHistory}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MockRateHistoryProvider extends RateHistoryProvider {
  override def get(code: String, limit: Option[Int]) = Future((0 to 100).map(r => RateHistory(r / 100.0, r)))
  override def saveRates(rates: Seq[Rate]) = Future()
}
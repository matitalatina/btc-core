package bitcoins.providers

import bitcoins.models.RateHistory
import bitcoins.viewmodels.Rate

import scala.concurrent.Future

trait RateHistoryProvider {
  def get(code: String, limit: Option[Int] = None): Future[Seq[RateHistory]]
  def saveRates(rates: Seq[Rate]): Future[Unit]
}

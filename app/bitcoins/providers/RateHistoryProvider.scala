package bitcoins.providers

import bitcoins.viewmodels.{Rate, RateHistory}

import scala.concurrent.Future

trait RateHistoryProvider {
  def get(code: String, limit: Option[Int] = None): Future[Seq[RateHistory]]
  def saveRates(rates: Seq[Rate]): Future[Unit]
}

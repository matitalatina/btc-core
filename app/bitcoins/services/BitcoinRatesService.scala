package bitcoins.services

import bitcoins.viewmodels.Rate

import scala.concurrent.Future

trait BitcoinRatesService {
  def fetch(): Future[Option[Seq[Rate]]]
}





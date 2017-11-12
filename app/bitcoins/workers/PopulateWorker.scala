package bitcoins.workers

import scala.concurrent.Future

trait PopulateWorker {
  def populateBitcoinRates(): Future[Unit]
}

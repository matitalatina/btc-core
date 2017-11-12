package bitcoins.mocks

import bitcoins.Fixtures
import bitcoins.services.BitcoinRatesService
import bitcoins.viewmodels.Rate

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MockBitcoinRatesService extends BitcoinRatesService {
  override def fetch(): Future[Option[Seq[Rate]]] = Future(Option(Seq(Fixtures.rateBtc, Fixtures.rateEur)))
}

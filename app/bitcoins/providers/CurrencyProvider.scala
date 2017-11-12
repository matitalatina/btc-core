package bitcoins.providers

import bitcoins.models.Currency

import scala.concurrent.Future

trait CurrencyProvider {
  def getAll: Future[Set[Currency]]
  def get(code: String): Future[Option[Currency]]
  def save(currencies: Set[Currency]): Future[Set[Currency]]
}

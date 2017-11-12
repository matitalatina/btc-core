package bitcoins.providers
import javax.inject.Inject

import bitcoins.models.Currency

import scala.concurrent.{ExecutionContext, Future}

class MemoryCurrencyProvider @Inject() (implicit val ec: ExecutionContext) extends CurrencyProvider {
  override def getAll = Future(repo)

  override def get(code: String) = Future(repo.find(_.code == code))

  override def save(currencies: Set[Currency]) = Future {
    repo = repo ++ currencies
    repo
  }

  var repo: Set[Currency] = Set()
}

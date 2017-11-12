package bitcoins.providers

import javax.inject.{Inject, _}

import bitcoins.models.Currency

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MemoryCurrencyProvider @Inject()(implicit ec: ExecutionContext) extends CurrencyProvider {
  override def getAll = Future(repo)

  override def get(code: String) = Future(repo.find(_.code == code))

  override def save(currencies: Set[Currency]) = Future {
    repo = repo ++ currencies
    repo
  }

  var repo: Set[Currency] = Set()
}

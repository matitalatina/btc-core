package bitcoins.providers

import javax.inject.{Inject, _}

import bitcoins.viewmodels.{Rate, RateHistory}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MemoryRateHistoryProvider @Inject()(implicit ec: ExecutionContext) extends RateHistoryProvider {
  override def get(code: String) = Future(repo.getOrElse(code, Seq()))

  override def saveRates(rates: Seq[Rate]) = Future {
    val now = System.currentTimeMillis()
    repo = rates.foldLeft(repo)((acc, rate) => {
      val currencyHistory = acc.get(rate.code)
      rate match {
        case r if !currencyHistory.flatMap(_.lastOption.map(_.rate)).contains(r.rate) =>
          acc + (r.code -> (currencyHistory.getOrElse(Seq()) :+ RateHistory(r.rate, now)))
        case _ => acc
      }
    })
  }

  var repo: Map[String, Seq[RateHistory]] = Map()
}

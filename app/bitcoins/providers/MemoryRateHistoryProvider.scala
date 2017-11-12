package bitcoins.providers

import javax.inject.Inject

import bitcoins.viewmodels.{Rate, RateHistory}

import scala.concurrent.{ExecutionContext, Future}

class MemoryRateHistoryProvider @Inject()(implicit val ec: ExecutionContext) extends RateHistoryProvider {
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

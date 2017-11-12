package bitcoins.providers

import javax.inject.Inject

import bitcoins.viewmodels.{Rate, RateHistory}

import scala.concurrent.{ExecutionContext, Future}

class MemoryRateHistoryProvider @Inject()(implicit val ec: ExecutionContext) extends RateHistoryProvider {
  override def get(code: String) = Future(repo.getOrElse(code, Seq()))

  override def saveRates(rates: Seq[Rate]) = Future {
    val now = System.currentTimeMillis()
    repo = rates.foldLeft(repo)((acc, rate) => {
      acc + (rate.code -> (acc.getOrElse(rate.code, Seq()) :+ RateHistory(rate.rate, now)))
    })
  }

  var repo: Map[String, Seq[RateHistory]] = Map()
}

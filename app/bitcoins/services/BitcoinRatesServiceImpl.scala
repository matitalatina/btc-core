package bitcoins.services

import javax.inject.Inject

import bitcoins.Formatters._
import bitcoins.viewmodels.Rate
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}

class BitcoinRatesServiceImpl @Inject()(ws: WSClient)(implicit ec: ExecutionContext) extends BitcoinRatesService {

  def fetch(): Future[Option[Seq[Rate]]] = ws
    .url(BitcoinRatesServiceImpl.URL)
    .get()
    .map {
      case response: WSResponse if response.status == 200 => response.json.validateOpt[Seq[Rate]].asOpt.flatten
      case _ => None
    }

}

object BitcoinRatesServiceImpl {
  val URL = "https://bitpay.com/api/rates"
}
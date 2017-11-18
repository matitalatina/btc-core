package bitcoins.services

import javax.inject.{Inject, _}

import bitcoins.Formatters._
import bitcoins.viewmodels.Rate
import play.api.Configuration
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BitcoinRatesServiceImpl @Inject()(
                                         config: Configuration,
                                         ws: WSClient,
                                       )(implicit ec: ExecutionContext) extends BitcoinRatesService {

  def fetch(): Future[Option[Seq[Rate]]] = ws
    .url(config.get[String]("bitcoins.rates.endpoint"))
    .get()
    .map {
      case response: WSResponse if response.status == 200 => response.json.validateOpt[Seq[Rate]].asOpt.flatten
      case _ => None
    }

}

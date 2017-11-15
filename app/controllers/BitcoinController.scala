package controllers

import javax.inject._

import bitcoins.Formatters._
import bitcoins.providers.{CurrencyProvider, RateHistoryProvider}
import bitcoins.viewmodels.RateStatsResponse
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class BitcoinController @Inject()(
                                   cc: ControllerComponents,
                                   currencyProvider: CurrencyProvider,
                                   rateHistoryProvider: RateHistoryProvider,
                                 )(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def currencies(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    currencyProvider.getAll.map(c => Ok(Json.toJson(c.toSeq.sortBy(_.code))))
  }

  def rateHistory(currencyCode: String, limit: Option[Int] = None): Action[AnyContent] =
    Action.async { implicit request: Request[AnyContent] =>
      for {
        history <- rateHistoryProvider.get(currencyCode, limit)
      } yield Ok(Json.toJson(history))
    }

}

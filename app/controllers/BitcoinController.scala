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

  def rateStats(currencyCode: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    for {
      currency <- currencyProvider.get(currencyCode)
      history <- rateHistoryProvider.get(currencyCode)
    } yield
      currency
        .map(c => Ok(Json.toJson(RateStatsResponse(c, Some(history)))))
        .getOrElse(NotFound)
  }

}

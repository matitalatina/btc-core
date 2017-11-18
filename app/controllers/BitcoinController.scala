package controllers

import javax.inject._

import bitcoins.Formatters._
import bitcoins.providers.{CurrencyProvider, RateHistoryProvider}
import bitcoins.stats.RateStatsCalculator
import bitcoins.viewmodels.RateStatsResponse
import play.api.libs.json.Json
import play.api.mvc.{Action, _}

import scala.concurrent.ExecutionContext

@Singleton
class BitcoinController @Inject()(
                                   cc: ControllerComponents,
                                   currencyProvider: CurrencyProvider,
                                   rateHistoryProvider: RateHistoryProvider,
                                   rateStatsCalculator: RateStatsCalculator,
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

  def get(currencyCode: String): Action[AnyContent] =
    Action.async { implicit request: Request[AnyContent] =>
      for {
        history <- rateHistoryProvider.get(currencyCode)
      } yield
        history.lastOption match {
          case Some(h) => Ok(Json.toJson(RateStatsResponse(h, rateStatsCalculator.stats(history))))
          case _ => NotFound
        }
    }

}

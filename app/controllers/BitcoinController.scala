package controllers

import javax.inject._

import akka.actor.{ActorRef, ActorSystem}
import akka.stream.Materializer
import bitcoins.Formatters._
import bitcoins.actors.{ListenerSocketActor, UpdateRoomActor}
import bitcoins.providers.{CurrencyProvider, RateHistoryProvider}
import bitcoins.stats.RateStatsCalculator
import bitcoins.viewmodels.RateStatsResponse
import play.api.libs.json.{JsValue, Json}
import play.api.libs.streams.ActorFlow
import play.api.mvc.{Action, _}

import scala.concurrent.ExecutionContext

@Singleton
class BitcoinController @Inject()(
                                   cc: ControllerComponents,
                                   currencyProvider: CurrencyProvider,
                                   rateHistoryProvider: RateHistoryProvider,
                                   rateStatsCalculator: RateStatsCalculator,
                                   @Named(UpdateRoomActor.name) updateRoom: ActorRef,
                                 )(implicit ec: ExecutionContext, system: ActorSystem, mat: Materializer)
  extends AbstractController(cc) {

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

  def socket(currencyCode: String) = WebSocket.accept[Any, JsValue] { request =>
    ActorFlow.actorRef(
      ListenerSocketActor.props(
        currencyCode,
        updateRoom,
        rateHistoryProvider,
        rateStatsCalculator,
      )
    )
  }

}

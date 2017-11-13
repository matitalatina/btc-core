package controllers

import javax.inject._

import bitcoins.Formatters._
import bitcoins.providers.CurrencyProvider
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class BitcoinController @Inject()(
                                   cc: ControllerComponents,
                                   currencyProvider: CurrencyProvider,
                                 )(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def currencies(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    currencyProvider.getAll.map(c => Ok(Json.toJson(c.toSeq.sortBy(_.code))))
  }

}

package bitcoins.models

import bitcoins.viewmodels.Rate

case class Currency(code: String, name: String)

object Currency {
  def fromRate(rate: Rate) = Currency(rate.code, rate.name)
}

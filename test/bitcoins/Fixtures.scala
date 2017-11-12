package bitcoins

import bitcoins.models.Currency

object Fixtures {
  val btcCode = "BTC"
  val btcName = "Bitcoin"
  val eurCode = "EUR"
  val eurName = "Euro"
  val currencyBtc = Currency(btcCode, "Bitcoin")
  val currencyEur = Currency(eurCode, "Euro")
}

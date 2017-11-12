package bitcoins

import bitcoins.models.Currency
import bitcoins.viewmodels.Rate

object Fixtures {
  val codeBtc = "BTC"
  val nameBtc = "Bitcoin"
  val codeEur = "EUR"
  val nameEur = "Euro"
  val currencyBtc = Currency(codeBtc, nameBtc)
  val currencyEur = Currency(codeEur, nameEur)
  val rateBtc = Rate(codeBtc, nameBtc, 0.12)
  val rateEur = Rate(codeEur, nameEur, 0.13)
}

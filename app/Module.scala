import bitcoins.providers.{CurrencyProvider, MemoryCurrencyProvider, MemoryRateHistoryProvider, RateHistoryProvider}
import bitcoins.services.{BitcoinRatesService, BitcoinRatesServiceImpl}
import com.google.inject.AbstractModule

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[CurrencyProvider]).to(classOf[MemoryCurrencyProvider])
    bind(classOf[BitcoinRatesService]).to(classOf[BitcoinRatesServiceImpl])
    bind(classOf[RateHistoryProvider]).to(classOf[MemoryRateHistoryProvider])
  }
}

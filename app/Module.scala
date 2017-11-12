import bitcoins.jobs.BitcoinRateFetchJob
import bitcoins.providers.{CurrencyProvider, MemoryCurrencyProvider, MemoryRateHistoryProvider, RateHistoryProvider}
import bitcoins.services.{BitcoinRatesService, BitcoinRatesServiceImpl}
import bitcoins.workers.{PopulateWorker, PopulateWorkerImpl}
import com.google.inject.AbstractModule
import play.api.inject.{SimpleModule, _}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[CurrencyProvider]).to(classOf[MemoryCurrencyProvider])
    bind(classOf[BitcoinRatesService]).to(classOf[BitcoinRatesServiceImpl])
    bind(classOf[RateHistoryProvider]).to(classOf[MemoryRateHistoryProvider])
    bind(classOf[PopulateWorker]).to(classOf[PopulateWorkerImpl])
  }
}

class JobsModule extends SimpleModule(bind[BitcoinRateFetchJob].toSelf.eagerly())

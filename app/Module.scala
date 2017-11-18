import bitcoins.actors.UpdateRoomActor
import bitcoins.jobs.BitcoinRateFetchJob
import bitcoins.providers.{CurrencyProvider, MemoryCurrencyProvider, MemoryRateHistoryProvider, RateHistoryProvider}
import bitcoins.services.{BitcoinRatesService, BitcoinRatesServiceImpl}
import bitcoins.stats.{RateStatsCalculator, RateStatsCalculatorImpl}
import bitcoins.workers.{PopulateWorker, PopulateWorkerImpl}
import com.google.inject.AbstractModule
import play.api.inject.{SimpleModule, _}
import play.api.libs.concurrent.AkkaGuiceSupport

class Module extends AbstractModule with AkkaGuiceSupport {
  override def configure(): Unit = {
    bind(classOf[CurrencyProvider]).to(classOf[MemoryCurrencyProvider])
    bind(classOf[BitcoinRatesService]).to(classOf[BitcoinRatesServiceImpl])
    bind(classOf[RateHistoryProvider]).to(classOf[MemoryRateHistoryProvider])
    bind(classOf[RateStatsCalculator]).to(classOf[RateStatsCalculatorImpl])
    bind(classOf[PopulateWorker]).to(classOf[PopulateWorkerImpl])
    bindActor[UpdateRoomActor](UpdateRoomActor.name)
  }
}

class JobsModule extends SimpleModule(bind[BitcoinRateFetchJob].toSelf.eagerly())

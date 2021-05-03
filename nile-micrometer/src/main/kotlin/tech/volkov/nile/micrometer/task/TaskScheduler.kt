package tech.volkov.nile.micrometer.task

import kotlinx.coroutines.launch
import mu.KLogging
import tech.volkov.nile.micrometer.context.MetricContext
import tech.volkov.nile.micrometer.executor.NileExecutor
import tech.volkov.nile.micrometer.gauge.MetricsRegister
import tech.volkov.nile.micrometer.registry.NileRegistry.Companion.globalRegistry
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

class TaskScheduler(
    private val nileExecutor: NileExecutor,
    private val defaultScrapeInterval: Duration,
    private val metricsRegister: MetricsRegister
) {

    init {
        nileExecutor.scheduledExecutorService.scheduleAtFixedRate(
            { runUpdateMetrics() },
            0,
            UPDATE_PERIOD_IN_MILLIS,
            TimeUnit.MILLISECONDS
        )
    }

    private fun runUpdateMetrics() = globalRegistry.forEach {
        nileExecutor.coroutineScope.launch { updateMetricIfNeeded(it.value) }
    }

    private fun updateMetricIfNeeded(metricContext: MetricContext) = with(metricContext) {
        if (nextScrapeInterval.isBefore(LocalDateTime.now())) {
            metricsRegister.extractValueAndRegisterMetric(metricContext)
            globalRegistry[name]?.nextScrapeInterval = LocalDateTime.now()
                .plus(scrapeIntervalInMillis(), ChronoUnit.MILLIS)
        }
    }

    private fun MetricContext.scrapeIntervalInMillis() = scrapeInterval?.toMillis()
        ?: defaultScrapeInterval.toMillis()

    companion object : KLogging() {
        private const val UPDATE_PERIOD_IN_MILLIS = 1000L
    }
}

package tech.volkov.nile.micrometer.task

import mu.KLogging
import tech.volkov.nile.micrometer.context.MetricContext
import tech.volkov.nile.micrometer.gauge.MetricsRegister
import java.time.Duration
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class TaskScheduler(
    corePoolSize: Int,
    private val defaultScrapeInterval: Duration,
    private val metricsRegister: MetricsRegister
) {

    private val scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize)

    fun schedule(metricContext: MetricContext) {
        scheduledExecutorService.scheduleAtFixedRate(
            { metricsRegister.extractValueAndRegisterMetric(metricContext) },
            0,
            metricContext.metricParametersContext.scrapeInterval?.toMillis()
                ?: defaultScrapeInterval.toMillis(),
            TimeUnit.MILLISECONDS
        )
    }

    companion object : KLogging()
}

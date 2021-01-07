package tech.volkov.nilecore.task

import mu.KLogging
import tech.volkov.nilecore.context.MetricContext
import tech.volkov.nilecore.gauge.MetricsRegister
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class TaskScheduler(
    private val metricsRegister: MetricsRegister
) {

    private val schedulers: MutableList<ScheduledExecutorService> = mutableListOf()

    fun schedule(metricContext: MetricContext) {
        val scheduler = Executors.newScheduledThreadPool(1)
        schedulers.add(scheduler)
        scheduler.scheduleAtFixedRate(
            { metricsRegister.extractValueAndRegisterMetric(metricContext) },
            0,
            metricContext.scrapeInterval,
            TimeUnit.SECONDS
        )
    }

    companion object : KLogging()
}

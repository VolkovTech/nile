package tech.volkov.nile.micrometer.builder

import mu.KLogging
import tech.volkov.nile.micrometer.builder.Nile.Companion.DEFAULT_CORE_POOL_SIZE
import tech.volkov.nile.micrometer.builder.Nile.Companion.DEFAULT_SCRAPE_INTERVAL
import tech.volkov.nile.micrometer.context.MetricContext
import tech.volkov.nile.micrometer.context.MetricParametersContext
import tech.volkov.nile.micrometer.gauge.MetricsRegister
import tech.volkov.nile.micrometer.task.TaskScheduler
import java.time.Duration

class Nile private constructor(
    /**
     * Core pool size for task scheduler, that will collect and register metrics.
     *
     * Note: in order to have correct metrics collection, the value has to be
     * more or equal to number of metrics, otherwise delay in collection is possible.
     *
     * Default value is [DEFAULT_CORE_POOL_SIZE].
     */
    corePoolSize: Int,
    /**
     * The time interval between each metrics collection, this value is used
     * in case no [MetricContext.metricParametersContext.scrapeInterval] is specified.
     *
     * Default value is [DEFAULT_SCRAPE_INTERVAL].
     */
    defaultScrapeInterval: Duration
) {

    private var metricsRegister: MetricsRegister = MetricsRegister()
    private var taskScheduler: TaskScheduler = TaskScheduler(corePoolSize, defaultScrapeInterval, metricsRegister)

    companion object : KLogging() {
        private const val DEFAULT_CORE_POOL_SIZE = 1
        private val DEFAULT_SCRAPE_INTERVAL = Duration.ofMinutes(1)

        fun builder() = NileBuilder()

        class NileBuilder {
            private var corePoolSize = DEFAULT_CORE_POOL_SIZE
            private var defaultScrapeInterval = DEFAULT_SCRAPE_INTERVAL

            fun corePoolSize(poolSize: Int): NileBuilder = apply {
                corePoolSize = poolSize
            }

            fun defaultScrapeInterval(scrapeInterval: Duration): NileBuilder = apply {
                defaultScrapeInterval = scrapeInterval
            }

            fun build() = Nile(corePoolSize, defaultScrapeInterval)
                .also { logger.info { "Nile configured with properties: \n\n$this" } }

            override fun toString() = buildString {
                appendLine("scheduler.core.pool.size: $corePoolSize")
                appendLine("default.scrape.interval: $defaultScrapeInterval")
            }
        }
    }

    /**
     * Builder for [MetricContext]
     */
    fun metric(
        name: String,
        description: String = "",
        init: MetricParametersContext.() -> Unit
    ) = MetricContext(name, description, MetricParametersContext().apply(init))
        .also { taskScheduler.schedule(it) }
}

package tech.volkov.nile.micrometer.builder

import mu.KLogging
import tech.volkov.nile.micrometer.context.MetricContext
import tech.volkov.nile.micrometer.context.MetricParametersContext
import tech.volkov.nile.micrometer.gauge.MetricsRegister
import tech.volkov.nile.micrometer.task.TaskScheduler
import java.time.Duration

class Nile(
    corePoolSize: Int = DEFAULT_CORE_POOL_SIZE,
    defaultScrapeInterval: Duration = DEFAULT_SCRAPE_INTERVAL
) {

    private var metricsRegister: MetricsRegister = MetricsRegister()
    private var taskScheduler: TaskScheduler = TaskScheduler(corePoolSize, defaultScrapeInterval, metricsRegister)

    companion object : KLogging() {
        private const val DEFAULT_CORE_POOL_SIZE = 1
        private val DEFAULT_SCRAPE_INTERVAL = Duration.ofSeconds(15)

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
                .also { logger.info { "Nile configured to with properties: $this" } }

            override fun toString() = "schedulerCorePoolSize: $corePoolSize, " +
                "defaultScrapeInterval: $defaultScrapeInterval"
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

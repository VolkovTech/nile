package tech.volkov.nile.micrometer.scheduler

import mu.KLogging
import tech.volkov.nile.micrometer.context.MetricContext
import tech.volkov.nile.micrometer.executor.NileExecutor
import tech.volkov.nile.micrometer.gauge.MetricsRegister
import tech.volkov.nile.micrometer.scheduler.NileScheduler.Companion.DEFAULT_CORE_POOL_SIZE
import tech.volkov.nile.micrometer.scheduler.NileScheduler.Companion.DEFAULT_SCRAPE_INTERVAL
import tech.volkov.nile.micrometer.task.TaskScheduler
import java.time.Duration

class NileScheduler private constructor(
    /**
     * Core pool size for task scheduler, that will collect and register metrics.
     *
     * Note: in order to have correct metrics collection, the value has to be
     * more or equal to number of metrics, otherwise delay in collection is possible.
     *
     * Default value is [DEFAULT_CORE_POOL_SIZE].
     */
    corePoolSize: Int,
    maximumPoolSize: Int,
    keepAliveTime: Long,
    queueCapacity: Int,
    /**
     * The time interval between each metrics collection, this value is used
     * in case no [MetricContext.scrapeInterval] is specified.
     *
     * Default value is [DEFAULT_SCRAPE_INTERVAL].
     */
    defaultScrapeInterval: Duration
) {

    private var metricsRegister: MetricsRegister = MetricsRegister()
    private var nileExecutor = NileExecutor(corePoolSize, maximumPoolSize, keepAliveTime, queueCapacity)
    private var taskScheduler: TaskScheduler = TaskScheduler(nileExecutor, defaultScrapeInterval, metricsRegister)

    companion object : KLogging() {
        private const val DEFAULT_CORE_POOL_SIZE = 4
        private const val DEFAULT_MAXIMUM_POOL_SIZE = 6
        private const val DEFAULT_KEEP_ALIVE_TIME = 200L
        private const val DEFAULT_QUEUE_CAPACITY = 5
        private val DEFAULT_SCRAPE_INTERVAL = Duration.ofMinutes(1)

        fun builder() = NileSchedulerBuilder()

        class NileSchedulerBuilder {
            private var corePoolSize: Int = DEFAULT_CORE_POOL_SIZE
            private var maximumPoolSize: Int = DEFAULT_MAXIMUM_POOL_SIZE
            private var keepAliveTime: Long = DEFAULT_KEEP_ALIVE_TIME
            private var queueCapacity: Int = DEFAULT_QUEUE_CAPACITY
            private var defaultScrapeInterval = DEFAULT_SCRAPE_INTERVAL

            fun corePoolSize(poolSize: Int): NileSchedulerBuilder = apply {
                corePoolSize = poolSize
            }

            fun maximumPoolSize(poolSize: Int): NileSchedulerBuilder = apply {
                maximumPoolSize = poolSize
            }

            fun keepAliveTime(keepAliveTimeInMillis: Long): NileSchedulerBuilder = apply {
                keepAliveTime = keepAliveTimeInMillis
            }

            fun queueCapacity(capacity: Int): NileSchedulerBuilder = apply {
                queueCapacity = capacity
            }

            fun defaultScrapeInterval(scrapeInterval: Duration): NileSchedulerBuilder = apply {
                defaultScrapeInterval = scrapeInterval
            }

            fun build() = NileScheduler(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                queueCapacity,
                defaultScrapeInterval
            )
                .also { logger.info { "Nile configured with properties: $this" } }

            override fun toString() = listOf(
                "coroutines.core.pool.size: $corePoolSize",
                "coroutines.maximum.pool.size: $maximumPoolSize",
                "coroutines.keep.alive.time: $keepAliveTime",
                "coroutines.queue.capacity: $queueCapacity",
                "default.scrape.interval: $defaultScrapeInterval"
            )
                .joinToString()
                .let { "[$it]" }
        }
    }

    /**
     * Builder for [MetricContext]
     */
    fun scheduledMetric(
        name: String,
        description: String = "",
        scrapeInterval: Duration? = null,
        tags: Map<String, String> = emptyMap(),
        value: () -> Double?
    ) = MetricContext(name, description, tags, scrapeInterval, value = value).register()
}

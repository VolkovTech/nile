package tech.volkov.nilecore.builder

import tech.volkov.nilecore.context.MetricContext
import tech.volkov.nilecore.gauge.MetricsRegister
import tech.volkov.nilecore.task.TaskScheduler

class Nile {

    private val metricsRegister = MetricsRegister()
    private val taskScheduler = TaskScheduler(metricsRegister)

    companion object {
        fun builder() = Nile()
    }

    /**
     * Builder for [MetricContext]
     */
    fun metric(
        name: String,
        description: String = "",
        scrapeInterval: Long = 15,
        value: () -> Double? = { 0.0 }
    ) = MetricContext(name, description, scrapeInterval, value)
        .also { taskScheduler.schedule(it) }
}

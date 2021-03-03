package tech.volkov.nile.micrometer.builder

import tech.volkov.nile.micrometer.context.MetricContext
import tech.volkov.nile.micrometer.context.MetricParametersContext
import tech.volkov.nile.micrometer.gauge.MetricsRegister
import tech.volkov.nile.micrometer.task.TaskScheduler

class Nile {

    private val metricsRegister = MetricsRegister()
    private val taskScheduler = TaskScheduler(metricsRegister)

    companion object {
        fun builder() = Builder()

        class Builder {
            fun build(): Nile {
                return Nile()
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

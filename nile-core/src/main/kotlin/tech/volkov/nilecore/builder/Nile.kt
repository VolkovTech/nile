package tech.volkov.nilecore.builder

import tech.volkov.nilecore.context.MetricContext
import tech.volkov.nilecore.context.MetricParametersContext
import tech.volkov.nilecore.gauge.MetricsRegister
import tech.volkov.nilecore.task.TaskScheduler

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

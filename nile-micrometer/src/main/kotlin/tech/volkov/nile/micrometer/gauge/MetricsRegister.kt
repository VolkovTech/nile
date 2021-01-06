package tech.volkov.nile.micrometer.gauge

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.Metrics
import mu.KLogging
import tech.volkov.nile.micrometer.context.MetricContext

class MetricsRegister {

    private val gaugeMetricMap = mutableMapOf<String, Double>()

    fun extractValueAndRegisterMetric(metricContext: MetricContext) = with(metricContext) {
        value().let { gaugeMetricMap[name] = it ?: 0.0 }
            .run { registerGaugeMetric(name, description) }
            .let { logger.debug { "Updated metric [$name] with value [$it]" } }
    }

    private fun registerGaugeMetric(
        metricName: String,
        description: String,
        tags: Map<String, String> = emptyMap()
    ) = Gauge.builder(metricName, gaugeMetricMap) { it[metricName] ?: 0.0 }
        .also { tags.forEach { (tagName, tagValue) -> it.tag(tagName, tagValue) } }
        .description(description)
        .register(Metrics.globalRegistry)
        .value()

    companion object : KLogging()
}

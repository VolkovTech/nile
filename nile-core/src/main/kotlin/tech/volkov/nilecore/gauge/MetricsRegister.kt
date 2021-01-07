package tech.volkov.nilecore.gauge

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.Metrics
import mu.KLogging
import tech.volkov.nilecore.context.MetricContext

class MetricsRegister {

    fun extractValueAndRegisterMetric(metricContext: MetricContext) = with(metricContext) {
        val metricValue = value()
        registerGaugeMetric(name, description, value = metricValue)
        logger.debug { "Updated metric [$name] with value [$metricValue]" }
    }

    private fun registerGaugeMetric(
        metricName: String,
        description: String,
        tags: Map<String, String> = emptyMap(),
        value: Double?
    ) {
        Gauge.builder(metricName) { value ?: 0.0 }
            .also { tags.forEach { (tagName, tagValue) -> it.tag(tagName, tagValue) } }
            .description(description)
            .register(Metrics.globalRegistry)
    }

    companion object : KLogging()
}

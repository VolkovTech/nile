package tech.volkov.nile.micrometer.gauge

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.Metrics
import mu.KLogging
import tech.volkov.nile.micrometer.context.MetricContext

class MetricsRegister {

    private val gaugeMetricMap = mutableMapOf<MetricContext, Double>()

    fun extractValueAndRegisterMetric(metricContext: MetricContext) = with(metricContext) {
        val metricValue = value()
        gaugeMetricMap[this] = metricValue ?: 0.0
        registerGaugeMetric(name, description, metricContext = this)
        logger.debug { "Updated metric [$name] with value [$metricValue]" }
    }

    private fun registerGaugeMetric(
        metricName: String,
        description: String,
        tags: Map<String, String> = emptyMap(),
        metricContext: MetricContext
    ) {
        Gauge.builder(metricName, gaugeMetricMap) { gaugeMetricMap[metricContext] ?: 0.0 }
            .also { tags.forEach { (tagName, tagValue) -> it.tag(tagName, tagValue) } }
            .description(description)
            .register(Metrics.globalRegistry)
    }

    companion object : KLogging()
}

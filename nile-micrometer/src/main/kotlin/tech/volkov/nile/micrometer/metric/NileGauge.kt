package tech.volkov.nile.micrometer.metric

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Tag
import tech.volkov.nile.micrometer.global.STATUS_FAILURE
import tech.volkov.nile.micrometer.global.STATUS_SUCCESS
import tech.volkov.nile.micrometer.global.STATUS_TAG_NAME
import tech.volkov.nile.micrometer.util.runBlockAndCatchError

private val gaugeMap: MutableMap<String, Double> = mutableMapOf()

fun nileGauge(
    name: String,
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    value: () -> Double = { 0.0 }
) = runBlockAndCatchError(value) { metricValue: Double?, isSuccess: Boolean ->
    gaugeMap[name] = metricValue ?: 0.0
    buildGauge(name, description, tags, isSuccess)
}

private fun buildGauge(
    name: String,
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    isSuccess: Boolean = true
) {
    Gauge.builder(name) { gaugeMap[name] ?: 0.0 }
        .description(description)
        .tags(tags.map { Tag.of(it.key, it.value) })
        .tag(STATUS_TAG_NAME, if (isSuccess) STATUS_SUCCESS else STATUS_FAILURE)
        .register(Metrics.globalRegistry)
}

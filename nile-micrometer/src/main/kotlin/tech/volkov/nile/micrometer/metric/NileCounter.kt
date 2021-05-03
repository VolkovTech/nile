package tech.volkov.nile.micrometer.metric

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Tag
import tech.volkov.nile.micrometer.global.STATUS_FAILURE
import tech.volkov.nile.micrometer.global.STATUS_SUCCESS
import tech.volkov.nile.micrometer.global.STATUS_TAG_NAME

fun incrementCounter(
    metricName: String,
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    isSuccess: Boolean = true,
    amount: () -> Double = { 1.0 }
) {
    Counter
        .builder(metricName)
        .description(description)
        .tags(tags.map { Tag.of(it.key, it.value) })
        .tag(STATUS_TAG_NAME, if (isSuccess) STATUS_SUCCESS else STATUS_FAILURE)
        .register(Metrics.globalRegistry)
        .increment(amount())
}

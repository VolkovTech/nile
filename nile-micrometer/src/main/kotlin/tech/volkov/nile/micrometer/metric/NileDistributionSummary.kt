package tech.volkov.nile.micrometer.metric

import io.micrometer.core.instrument.DistributionSummary
import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Tag
import tech.volkov.nile.micrometer.global.DEFAULT_PERCENTILES
import tech.volkov.nile.micrometer.global.STATUS_FAILURE
import tech.volkov.nile.micrometer.global.STATUS_SUCCESS
import tech.volkov.nile.micrometer.global.STATUS_TAG_NAME

fun nileDistributionSummary(
    name: String,
    unit: String = "",
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    percentiles: List<Double> = DEFAULT_PERCENTILES,
    isSuccess: Boolean = true,
    amount: () -> Double = { 1.0 }
) {
    DistributionSummary
        .builder(name)
        .baseUnit(unit)
        .description(description)
        .tags(tags.map { Tag.of(it.key, it.value) })
        .tag(STATUS_TAG_NAME, if (isSuccess) STATUS_SUCCESS else STATUS_FAILURE)
        .publishPercentiles(*percentiles.toDoubleArray())
        .register(Metrics.globalRegistry)
        .record(amount())
}

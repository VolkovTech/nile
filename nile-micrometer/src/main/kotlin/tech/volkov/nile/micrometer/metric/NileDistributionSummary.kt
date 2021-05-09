package tech.volkov.nile.micrometer.metric

import io.micrometer.core.instrument.DistributionSummary
import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Tag
import tech.volkov.nile.micrometer.global.DEFAULT_PERCENTILES
import tech.volkov.nile.micrometer.global.STATUS_FAILURE
import tech.volkov.nile.micrometer.global.STATUS_SUCCESS
import tech.volkov.nile.micrometer.global.STATUS_TAG_NAME
import tech.volkov.nile.micrometer.util.runBlockAndCatchError

fun nileDistributionSummary(
    name: String,
    unit: String = "",
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    percentiles: List<Double> = DEFAULT_PERCENTILES,
    block: () -> Double = { 0.0 }
) = runBlockAndCatchError(block) { metricValue: Double?, isSuccess: Boolean ->
    DistributionSummary
        .builder(name)
        .baseUnit(unit)
        .description(description)
        .tags(tags.map { tag -> Tag.of(tag.key, tag.value) })
        .tag(STATUS_TAG_NAME, if (isSuccess) STATUS_SUCCESS else STATUS_FAILURE)
        .publishPercentiles(*percentiles.toDoubleArray())
        .register(Metrics.globalRegistry)
        .record(metricValue ?: 0.0)
}

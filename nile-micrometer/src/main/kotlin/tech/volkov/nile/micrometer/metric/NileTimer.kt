package tech.volkov.nile.micrometer.metric

import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Timer
import tech.volkov.nile.micrometer.util.DEFAULT_PERCENTILES
import tech.volkov.nile.micrometer.util.STATUS_FAILURE
import tech.volkov.nile.micrometer.util.STATUS_SUCCESS
import tech.volkov.nile.micrometer.util.STATUS_TAG_NAME
import tech.volkov.nile.micrometer.util.runBlockAndCatchError

fun <T> nileTimer(
    name: String,
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    percentiles: List<Double> = DEFAULT_PERCENTILES,
    block: () -> T
): T {
    val timerSample = startTimer()

    return runBlockAndCatchError(block) { _: Double?, isSuccess: Boolean ->
        stopTimer(timerSample, name, description, tags, percentiles, isSuccess)
    }
}

private fun startTimer(): Timer.Sample = Timer.start(Metrics.globalRegistry)

private fun stopTimer(
    timerSample: Timer.Sample,
    name: String,
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    percentiles: List<Double> = DEFAULT_PERCENTILES,
    isSuccess: Boolean = true
) = timerSample.stop(
    Timer
        .builder(name)
        .description(description)
        .tags(tags.map { Tag.of(it.key, it.value) })
        .tag(STATUS_TAG_NAME, if (isSuccess) STATUS_SUCCESS else STATUS_FAILURE)
        .publishPercentiles(*percentiles.toDoubleArray())
        .register(Metrics.globalRegistry)
)

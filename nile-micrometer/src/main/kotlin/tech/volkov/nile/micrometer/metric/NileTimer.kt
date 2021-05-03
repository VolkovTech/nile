package tech.volkov.nile.micrometer.metric

import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Timer
import tech.volkov.nile.micrometer.global.STATUS_FAILURE
import tech.volkov.nile.micrometer.global.STATUS_SUCCESS
import tech.volkov.nile.micrometer.global.STATUS_TAG_NAME

fun <T> withTimer(
    metricName: String,
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    block: () -> T
): T {
    val timerSample = startTimer()

    return runCatching(block)
        .also { stopTimer(timerSample, metricName, description, tags, it.isSuccess) }
        .getOrThrow()
}

fun startTimer(): Timer.Sample = Timer.start(Metrics.globalRegistry)

fun stopTimer(
    timerSample: Timer.Sample,
    metricName: String,
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    isSuccess: Boolean = true
) = timerSample.stop(
    Timer.builder(metricName)
        .description(description)
        .tags(tags.map { Tag.of(it.key, it.value) })
        .tag(STATUS_TAG_NAME, if (isSuccess) STATUS_SUCCESS else STATUS_FAILURE)
        .register(Metrics.globalRegistry)
)

package tech.volkov.nile.micrometer.metric

import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Timer
import tech.volkov.nile.micrometer.global.STATUS_FAILURE
import tech.volkov.nile.micrometer.global.STATUS_SUCCESS
import tech.volkov.nile.micrometer.global.STATUS_TAG_NAME

fun <T> nileTimer(
    name: String,
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    block: () -> T
): T {
    val timerSample = startTimer()

    return runCatching(block)
        .also { stopTimer(timerSample, name, description, tags, it.isSuccess) }
        .getOrThrow()
}

private fun startTimer(): Timer.Sample = Timer.start(Metrics.globalRegistry)

private fun stopTimer(
    timerSample: Timer.Sample,
    name: String,
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    isSuccess: Boolean = true
) = timerSample.stop(
    Timer.builder(name)
        .description(description)
        .tags(tags.map { Tag.of(it.key, it.value) })
        .tag(STATUS_TAG_NAME, if (isSuccess) STATUS_SUCCESS else STATUS_FAILURE)
        .register(Metrics.globalRegistry)
)

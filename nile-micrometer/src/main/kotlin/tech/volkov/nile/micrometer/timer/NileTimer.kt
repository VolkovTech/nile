package tech.volkov.nile.micrometer.timer

import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Timer

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

fun startTimer() = Timer.start(Metrics.globalRegistry)

fun stopTimer(
    timerSample: Timer.Sample,
    metricName: String,
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    isSuccess: Boolean = true
) = timerSample.stop(
    Timer.builder(metricName)
        .also { tags.forEach { (tagName, tagValue) -> it.tag(tagName, tagValue) } }
        .tag("status", if (isSuccess) "OK" else "ERROR")
        .description(description)
        .register(Metrics.globalRegistry)
)

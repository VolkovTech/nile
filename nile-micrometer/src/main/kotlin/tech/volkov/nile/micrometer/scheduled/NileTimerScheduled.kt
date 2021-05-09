package tech.volkov.nile.micrometer.scheduled

import tech.volkov.nile.micrometer.metric.nileTimer
import tech.volkov.nile.micrometer.registry.NileScheduledRegistry.Companion.addScheduledTask
import tech.volkov.nile.micrometer.util.DEFAULT_PERCENTILES
import java.time.Duration

fun <T> nileTimerScheduled(
    name: String,
    description: String = "",
    tags: Map<String, String> = mapOf(),
    percentiles: List<Double> = DEFAULT_PERCENTILES,
    scrapeInterval: Duration = Duration.ofMillis(30),
    block: () -> T
) = addScheduledTask(name) {
    this.scrapeInterval = scrapeInterval
    this.block = { nileTimer(name, description, tags, percentiles, block) }
}

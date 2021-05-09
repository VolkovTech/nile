package tech.volkov.nile.micrometer.scheduled

import tech.volkov.nile.micrometer.global.DEFAULT_PERCENTILES
import tech.volkov.nile.micrometer.metric.nileTimer
import tech.volkov.nile.micrometer.registry.NileScheduledRegistry.Companion.addScheduledTask
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

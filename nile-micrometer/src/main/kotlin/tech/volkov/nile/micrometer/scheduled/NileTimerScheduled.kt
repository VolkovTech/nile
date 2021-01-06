package tech.volkov.nile.micrometer.scheduled

import tech.volkov.nile.micrometer.metric.nileTimer
import tech.volkov.nile.micrometer.registry.NileScheduledRegistry.Companion.addScheduledTask
import tech.volkov.nile.micrometer.util.DEFAULT_PERCENTILES
import tech.volkov.nile.micrometer.util.DEFAULT_SCRAPE_INTERVAL
import java.time.Duration

fun <T> nileTimerScheduled(
    name: String,
    description: String = "",
    tags: Map<String, String> = mapOf(),
    percentiles: List<Double> = DEFAULT_PERCENTILES,
    scrapeInterval: Duration = DEFAULT_SCRAPE_INTERVAL,
    block: () -> T
) = addScheduledTask(name) {
    this.scrapeInterval = scrapeInterval
    this.block = { nileTimer(name, description, tags, percentiles, block) }
}

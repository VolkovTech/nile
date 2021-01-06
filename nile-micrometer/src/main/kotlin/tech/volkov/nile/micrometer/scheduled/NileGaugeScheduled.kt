package tech.volkov.nile.micrometer.scheduled

import tech.volkov.nile.micrometer.metric.nileGauge
import tech.volkov.nile.micrometer.registry.NileScheduledRegistry.Companion.addScheduledTask
import tech.volkov.nile.micrometer.util.DEFAULT_SCRAPE_INTERVAL
import java.time.Duration

fun nileGaugeScheduled(
    name: String,
    description: String = "",
    tags: Map<String, String> = mapOf(),
    scrapeInterval: Duration = DEFAULT_SCRAPE_INTERVAL,
    block: () -> Double
) = addScheduledTask(name) {
    this.scrapeInterval = scrapeInterval
    this.block = { nileGauge(name, description, tags, block) }
}

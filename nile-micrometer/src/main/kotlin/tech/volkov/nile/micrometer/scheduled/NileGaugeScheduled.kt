package tech.volkov.nile.micrometer.scheduled

import tech.volkov.nile.micrometer.metric.nileGauge
import tech.volkov.nile.micrometer.registry.NileScheduledRegistry.Companion.addScheduledTask
import java.time.Duration

fun nileGaugeScheduled(
    name: String,
    description: String = "",
    tags: Map<String, String> = mapOf(),
    scrapeInterval: Duration = Duration.ofMillis(30),
    block: () -> Double
) = addScheduledTask(name) {
    this.scrapeInterval = scrapeInterval
    value = { nileGauge(name, description, tags, block) }
}

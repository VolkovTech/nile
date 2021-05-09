package tech.volkov.nile.micrometer.scheduled

import tech.volkov.nile.micrometer.metric.nileCounter
import tech.volkov.nile.micrometer.registry.NileScheduledRegistry.Companion.addScheduledTask
import tech.volkov.nile.micrometer.util.DEFAULT_SCRAPE_INTERVAL
import java.time.Duration

fun <T> nileCounterScheduled(
    name: String,
    description: String = "",
    tags: Map<String, String> = mapOf(),
    amount: Double = 1.0,
    scrapeInterval: Duration = DEFAULT_SCRAPE_INTERVAL,
    block: () -> T
) = addScheduledTask(name) {
    this.scrapeInterval = scrapeInterval
    this.block = { nileCounter(name, description, tags, amount) { block } }
}

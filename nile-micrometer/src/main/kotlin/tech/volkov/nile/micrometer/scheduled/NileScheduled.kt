package tech.volkov.nile.micrometer.scheduled

import tech.volkov.nile.micrometer.registry.NileScheduledRegistry
import tech.volkov.nile.micrometer.util.DEFAULT_SCRAPE_INTERVAL
import java.time.Duration

fun <T> nileScheduled(
    name: String,
    scrapeInterval: Duration = DEFAULT_SCRAPE_INTERVAL,
    block: () -> T
) = NileScheduledRegistry.addScheduledTask(name) {
    this.scrapeInterval = scrapeInterval
    this.block = block
}

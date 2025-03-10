package tech.volkov.nile.micrometer.scheduled

import tech.volkov.nile.micrometer.metric.nileDistributionSummary
import tech.volkov.nile.micrometer.registry.NileScheduledRegistry.Companion.addScheduledTask
import tech.volkov.nile.micrometer.util.DEFAULT_PERCENTILES
import tech.volkov.nile.micrometer.util.DEFAULT_SCRAPE_INTERVAL
import java.time.Duration

fun nileDistributionSummaryScheduled(
    name: String,
    unit: String = "",
    description: String = "",
    tags: Map<String, String> = mapOf(),
    percentiles: List<Double> = DEFAULT_PERCENTILES,
    scrapeInterval: Duration = DEFAULT_SCRAPE_INTERVAL,
    block: () -> Double
) = addScheduledTask(name) {
    this.scrapeInterval = scrapeInterval
    this.block = { nileDistributionSummary(name, unit, description, tags, percentiles, block) }
}

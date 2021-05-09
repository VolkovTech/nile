package tech.volkov.nile.micrometer.scheduled

import tech.volkov.nile.micrometer.metric.nileDistributionSummary
import tech.volkov.nile.micrometer.registry.NileScheduledRegistry.Companion.addScheduledTask
import java.time.Duration

fun nileDistributionSummaryScheduled(
    name: String,
    unit: String = "",
    description: String = "",
    tags: Map<String, String> = mapOf(),
    percentiles: List<Double> = listOf(0.5, 0.75, 0.9, 0.95, 0.99),
    scrapeInterval: Duration = Duration.ofMillis(30),
    block: () -> Double
) = addScheduledTask(name) {
    this.scrapeInterval = scrapeInterval
    value = { nileDistributionSummary(name, unit, description, tags, percentiles, block) }
}

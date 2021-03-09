package tech.volkov.nile.micrometer.context

import tech.volkov.nile.micrometer.annotation.MetricDSL
import tech.volkov.nile.micrometer.registry.NileRegistry
import java.time.Duration
import java.time.LocalDateTime

@MetricDSL
data class MetricContext(
    /**
     * Metric name separated by `_`, will be exposed to prometheus endpoint.
     */
    var name: String,
    /**
     * Metric description, will be exposed to prometheus endpoint.
     */
    var description: String,
    /**
     * Tags that will exported along with metric.
     */
    var tags: Map<String, String> = emptyMap(),
    /**
     * Scrape interval, how often the metric should be updated.
     */
    var scrapeInterval: Duration? = null,
    /**
     * Defines next scrape interval for particular metric, the value updates to
     * (LocalDateTime.now() + [scrapeInterval]) every time the metric value is collected.
     */
    var nextScrapeInterval: LocalDateTime = LocalDateTime.now(),
    /**
     * The function, by which the metric value will be extracted.
     */
    var value: () -> Double? = { 0.0 }
) {

    /**
     * Registers metric in [NileRegistry.globalRegistry].
     */
    fun register() = apply {
        NileRegistry.globalRegistry[name] = this
    }
}

package tech.volkov.nile.micrometer.context

import tech.volkov.nile.micrometer.annotation.MetricDSL
import java.time.Duration

@MetricDSL
class MetricContext(
    /**
     * Metric name separated by `_`, will be exposed to prometheus endpoint
     */
    var name: String,
    /**
     * Metric description, will be exposed to prometheus endpoint
     */
    var description: String,
    /**
     * Scrape interval, how often the metric should be updated
     */
    var scrapeInterval: Duration? = null,
    /**
     * The function, by which the metric value will be extracted
     */
    var value: () -> Double? = { 0.0 }
)

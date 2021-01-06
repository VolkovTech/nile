package tech.volkov.nilecore.context

import tech.volkov.nilecore.annotation.MetricDSL

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
     * Scrape interval in sec, how often the metric should be updated
     */
    var scrapeInterval: Int,
    /**
     * The function, by which the metric value will be extracted
     */
    var value: () -> Double?
)

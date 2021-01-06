package tech.volkov.nilecore.builder

import tech.volkov.nilecore.context.MetricContext

/**
 * Builder for [MetricContext]
 */
fun metric(
    name: String,
    description: String = "",
    scrapeInterval: Int = 15,
    value: () -> Double? = { 0.0 }
) = MetricContext(name, description, scrapeInterval, value)

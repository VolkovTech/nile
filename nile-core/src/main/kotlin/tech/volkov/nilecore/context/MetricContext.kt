package tech.volkov.nilecore.context

import tech.volkov.nilecore.annotation.MetricDSL
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
     * [MetricParametersContext] object that represents metric parameters
     */
    var metricParametersContext: MetricParametersContext
)

class MetricParametersContext {
    /**
     * Scrape interval, how often the metric should be updated
     */
    var scrapeInterval: Duration = Duration.ofSeconds(15)
    /**
     * The function, by which the metric value will be extracted
     */
    var value: () -> Double? = { 0.0 }
        private set

    fun value(block: () -> Double?) { value = block }
}

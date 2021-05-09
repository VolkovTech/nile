package tech.volkov.nile.micrometer.registry

import tech.volkov.nile.micrometer.util.DEFAULT_SCRAPE_INTERVAL
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class NileScheduledTask(
    /**
     * Metric name separated by `_`, will be exposed to prometheus endpoint.
     */
    var name: String,
    /**
     * Scrape interval, how often the metric should be updated.
     */
    var scrapeInterval: Duration? = null,
    /**
     * Defines next scrape time for particular metric, the value updates to
     * (LocalDateTime.now() + [scrapeInterval]) every time the metric value is collected.
     */
    var nextScrapeTime: LocalDateTime = LocalDateTime.now(),
    /**
     * The function that will run on schedule.
     */
    var block: () -> Any? = {}
) {
    fun updateNextScrapeTime() {
        nextScrapeTime = LocalDateTime.now().plus(
            (scrapeInterval ?: DEFAULT_SCRAPE_INTERVAL).toMillis(),
            ChronoUnit.MILLIS
        )
    }
}

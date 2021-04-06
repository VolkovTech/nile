package tech.volkov.nile.application.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.application.service.DogFactService
import tech.volkov.nile.micrometer.metric.nileCounter
import tech.volkov.nile.micrometer.metric.nileDistributionSummary
import tech.volkov.nile.micrometer.metric.nileGauge
import tech.volkov.nile.micrometer.metric.nileTimer
import tech.volkov.nile.micrometer.scheduled.nileCounterScheduled
import tech.volkov.nile.micrometer.scheduled.nileDistributionSummaryScheduled
import tech.volkov.nile.micrometer.scheduled.nileGaugeScheduled
import tech.volkov.nile.micrometer.scheduled.nileTimerScheduled
import java.time.Duration
import kotlin.random.Random

@RestController
@RequestMapping("dog-facts/function")
class NileFunctionsController(
    private val dogFactService: DogFactService
) {

    @GetMapping("counter")
    fun counter() = nileCounterScheduled(
        name = "requests_counter",
        description = "Counts how many request were executed to API",
        amount = 10.0,
        scrapeInterval = Duration.ofSeconds(10)
//        tags = mapOf("tagName" to "tagValue")
    ) {
        Thread.sleep(Random.nextLong(5000))
//        dogFactService.getFacts()
    }

    @GetMapping("timer")
    fun timer() = nileTimerScheduled(
        name = "response_time",
        description = "Response time for external API",
//        tags = mapOf("tagName" to "tagValue"),
        percentiles = listOf(0.5, 0.75, 0.9, 0.95, 0.99)
    ) {
        Thread.sleep(Random.nextLong(500))
//        dogFactService.getFacts()
    }

    @GetMapping("gauge")
    fun gauge() = nileGaugeScheduled(
        name = "requests_gauge",
        description = "Current value of size of returned list from API",
//        tags = mapOf("tagName" to "tagValue")
    ) {
        Random.nextDouble(100.0)
    }

    @GetMapping("distribution-summary")
    fun distributionSummary() = nileDistributionSummaryScheduled(
        name = "requests_distribution_summary",
        unit = "short",
        description = "Distribution summary for API requests",
//        tags = mapOf("tagName" to "tagValue")
    ) {
        Random.nextDouble(100.0)
    }
}

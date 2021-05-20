package tech.volkov.nile.application.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.application.service.DogFactService
import tech.volkov.nile.micrometer.metric.nileCounter
import tech.volkov.nile.micrometer.metric.nileDistributionSummary
import tech.volkov.nile.micrometer.metric.nileGauge
import tech.volkov.nile.micrometer.metric.nileTimer
import kotlin.random.Random

@RestController
@RequestMapping("dog-facts/function")
class NileFunctionsController(
    private val dogFactService: DogFactService
) {

    @GetMapping("counter")
    fun counter() = nileCounter(
        name = "requests_counter",
        description = "Counts how many request were executed to API",
//        tags = mapOf("tagName" to "tagValue")
    ) {
        dogFactService.getFacts()
    }

    @GetMapping("timer")
    fun timer() = nileTimer(
        name = "response_time",
        description = "Response time for external API",
//        tags = mapOf("tagName" to "tagValue"),
        percentiles = listOf(0.5, 0.75, 0.9, 0.95, 0.99)
    ) {
        Thread.sleep(Random.nextLong(500))
        dogFactService.getFacts()
    }

    @GetMapping("gauge")
    fun gauge() = nileGauge(
        name = "requests_gauge",
        description = "Current value of size of returned list from API",
//        tags = mapOf("tagName" to "tagValue")
    ) {
        dogFactService.getFacts().size.toDouble()
    }

    @GetMapping("distribution-summary")
    fun distributionSummary() = nileDistributionSummary(
        name = "requests_distribution_summary",
        unit = "short",
        description = "Distribution summary for API requests",
//        tags = mapOf("tagName" to "tagValue")
    ) {
        dogFactService.getFacts().size.toDouble()
    }
}

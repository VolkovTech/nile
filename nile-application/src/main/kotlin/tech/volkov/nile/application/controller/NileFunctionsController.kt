package tech.volkov.nile.application.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.application.service.DogFactService
import tech.volkov.nile.micrometer.metric.nileCounter
import tech.volkov.nile.micrometer.metric.nileDistributionSummary
import tech.volkov.nile.micrometer.metric.nileGauge
import tech.volkov.nile.micrometer.metric.nileTimer

@RestController
@RequestMapping("dog-facts/function")
class NileFunctionsController(
    private val dogFactService: DogFactService
) {

    @GetMapping("counter")
    fun counter() = nileCounter(
        name = "dog_facts_counter",
        description = "Counts how many request were executed to dog facts API",
        tags = mapOf("tagName" to "tagValue")
    ) {
        dogFactService.getFacts()
    }

    @GetMapping("timer")
    fun timer() = nileTimer(
        name = "dog_facts_timer",
        description = "Response time for dog facts API",
        tags = mapOf("tagName" to "tagValue"),
        percentiles = listOf(0.5, 0.75, 0.9, 0.95, 0.99)
    ) {
        dogFactService.getFacts()
    }

    @GetMapping("gauge")
    fun gauge() = nileGauge(
        name = "dog_facts_gauge",
        description = "Current value of size of dogs facts",
        tags = mapOf("tagName" to "tagValue")
    ) {
        dogFactService.getFacts().size.toDouble()
    }

    @GetMapping("distribution-summary")
    fun distributionSummary() = nileDistributionSummary(
        name = "dog_facts_distribution_summary",
        unit = "short",
        description = "Distribution summary for dogs facts",
        tags = mapOf("tagName" to "tagValue")
    ) {
        dogFactService.getFacts().size.toDouble()
    }
}

package tech.volkov.nile.application.controller

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.application.service.DogFactService
import tech.volkov.nile.micrometer.annotation.NileCounter
import tech.volkov.nile.micrometer.annotation.NileDistributionSummary
import tech.volkov.nile.micrometer.annotation.NileGauge
import tech.volkov.nile.micrometer.annotation.NileTimer

@RestController
@RequestMapping("dog-facts/annotation")
class NileAnnotationController(
    private val dogFactService: DogFactService
) {

    @GetMapping
    @NileCounter(
        name = "dog_facts_counter",
        description = "Counts how many request were executed to dog facts API",
        tags = ["tagName", "tagValue"],
        amount = 10.0
    )
    @NileTimer(
        name = "dog_facts_timer",
        description = "Response time for dog facts API",
        tags = ["tagName", "tagValue"],
        percentiles = [0.5, 0.75, 0.9, 0.95, 0.99]
    )
    @NileGauge(
        name = "dog_facts_gauge",
        description = "Current value of size of dogs facts",
        tags = ["tagName", "tagValue"]
    )
    @NileDistributionSummary(
        name = "dog_facts_distribution_summary",
        unit = "short",
        description = "Distribution summary for dogs facts",
        tags = ["tagName", "tagValue"],
        percentiles = [0.5, 0.75, 0.9, 0.95, 0.99]
    )
    fun getDogFacts() = dogFactService.getFacts()

    companion object : KLogging()
}

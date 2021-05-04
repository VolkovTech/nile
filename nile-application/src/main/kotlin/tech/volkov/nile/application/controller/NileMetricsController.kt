package tech.volkov.nile.application.controller

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.application.dto.MetricDto
import tech.volkov.nile.application.service.DogFactService
import tech.volkov.nile.micrometer.annotation.metric.NileCounter
import tech.volkov.nile.micrometer.annotation.metric.NileGauge
import tech.volkov.nile.micrometer.annotation.metric.NileTimer
import tech.volkov.nile.micrometer.scheduler.NileScheduler
import java.time.Duration
import kotlin.random.Random

@RestController
@RequestMapping("dog-facts")
class NileMetricsController(
    private val nileScheduler: NileScheduler,
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
    fun getDogFacts() = dogFactService.getFacts()

    @GetMapping("schedule")
    fun scheduleMetric(): MetricDto {
        val metricContext = nileScheduler.scheduleMetric(
            name = "random_number",
            description = "Returns random number between 0 to 9",
            scrapeInterval = Duration.ofSeconds(5)
        ) {
            Random.nextDouble(10.0)
        }

        return with(metricContext) {
            MetricDto(
                name = name,
                description = description,
                scrapeInterval = scrapeInterval,
                sampleValue = value()
            )
        }
    }

    companion object : KLogging()
}

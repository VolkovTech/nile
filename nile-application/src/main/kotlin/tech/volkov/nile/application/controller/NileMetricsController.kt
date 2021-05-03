package tech.volkov.nile.application.controller

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.application.dto.MetricDto
import tech.volkov.nile.application.service.DogFactService
import tech.volkov.nile.micrometer.metric.nileCounter
import tech.volkov.nile.micrometer.metric.nileGauge
import tech.volkov.nile.micrometer.metric.nileTimer
import tech.volkov.nile.micrometer.scheduler.NileScheduler
import java.time.Duration
import kotlin.random.Random

@RestController
@RequestMapping("dog-facts")
class NileMetricsController(
    private val nileScheduler: NileScheduler,
    private val dogFactService: DogFactService
) {

    @GetMapping("counter")
    fun counter() = dogFactService.getDogFacts().also {
        nileCounter(
            name = "dog_facts_counter",
            description = "Counter for dogs facts"
        )
    }

    @GetMapping("timer")
    fun timer() {
        nileTimer(
            name = "dog_facts_timer",
            description = "Time to execute call to cat fact API"
        ) {
            dogFactService.getDogFacts()
        }
    }

    @GetMapping("gauge")
    fun gauge() = dogFactService.getDogFacts().also {
        nileGauge(
            name = "dog_facts_gauge",
            description = "Gauge for dogs facts"
        ) {
            it.size.toDouble()
        }
    }

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

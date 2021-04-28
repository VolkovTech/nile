package tech.volkov.nile.application.controller

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.application.dto.MetricDto
import tech.volkov.nile.application.service.DogFactService
import tech.volkov.nile.micrometer.scheduler.NileScheduler
import java.time.Duration
import kotlin.random.Random

@RestController
@RequestMapping("dog-facts")
class NileMetricsController(
    private val nileScheduler: NileScheduler,
    private val dogFactService: DogFactService
) {

    @GetMapping("timer")
    fun withTimer() = dogFactService.getFactWithTimer()

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

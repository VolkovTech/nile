package tech.volkov.nile.application.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.application.dto.MetricDto
import tech.volkov.nile.micrometer.builder.Nile
import java.time.Duration
import kotlin.random.Random

@RestController
@RequestMapping("/metrics")
class NileMetricsController(
    private val nile: Nile
) {

    @GetMapping("schedule")
    fun scheduleMetric(): MetricDto {
        val metric = nile.metric(
            name = "random_number",
            description = "Returns random number between 0 to 9"
        ) {
            scrapeInterval = Duration.ofSeconds(5)

            value { Random.nextDouble(10.0) }
        }

        return with(metric) {
            MetricDto(
                name = name,
                description = description,
                scrapeInterval = metricParametersContext.scrapeInterval,
                sampleValue = metricParametersContext.value()
            )
        }
    }
}

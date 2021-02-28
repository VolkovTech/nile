package tech.volkov.nile.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.dto.MetricDto
import tech.volkov.nilecore.builder.Nile
import java.time.Duration
import kotlin.random.Random

@RestController
@RequestMapping("/metrics")
class NileMetricsController(
    private val nile: Nile
) {

    @GetMapping("increment")
    fun incrementMetric(): MetricDto {
        val metric = nile.metric(
            name = "random_number",
            description = "Returns random number between 0 to 9"
        ) {
            scrapeInterval = Duration.ofSeconds(5)

            value { Random.nextDouble(10.0) }
        }

        return with(metric) {
            MetricDto(
                name,
                description,
                metricParametersContext.scrapeInterval.toMillis(),
                metricParametersContext.value() ?: 0.0
            )
        }
    }
}

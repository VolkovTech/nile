package tech.volkov.nile.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.dto.MetricDto
import tech.volkov.nilecore.builder.metric
import kotlin.random.Random

@RestController
@RequestMapping("/metrics")
class NileMetricsController {

    @GetMapping("increment")
    fun incrementMetric(): MetricDto {
        val metric = metric(
            name = "random_number",
            description = "Returns random number between 0 to 9"
        ) {
            Random.nextInt(10).toDouble()
        }

        return with(metric) { MetricDto(name, description, scrapeInterval, value() ?: 0.0) }
    }
}

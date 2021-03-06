package tech.volkov.nile.application.controller

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import tech.volkov.nile.application.dto.MetricDto
import tech.volkov.nile.micrometer.builder.NileMicrometer
import tech.volkov.nile.micrometer.timer.withTimer
import java.time.Duration
import kotlin.random.Random

@RestController
@RequestMapping("/metrics")
class NileMetricsController(
    private val nileMicrometer: NileMicrometer,
    private val webClient: WebClient
) {

    data class CatFact(val fact: String, val length: Int)

    @GetMapping("timer")
    fun timerMetric(): CatFact? {
        val catFactResponse = withTimer(
            metricName = "cat_fact",
            description = "Time to execute call to cat fact API"
        ) {
            webClient
                .get()
                .uri {
                    UriComponentsBuilder.fromHttpUrl("https://catfact.ninja")
                        .pathSegment("fact")
                        .build()
                        .toUri()
                }
                .exchangeToMono { it.toEntity(CatFact::class.java) }
                .block()
        }

        return if (catFactResponse?.statusCode?.is2xxSuccessful == true) {
            logger.info { "Successfully received cat fact: [${catFactResponse.body?.fact}]" }
            catFactResponse.body
        } else {
            logger.error { "Failed to receive cat fact" }
            null
        }
    }

    @GetMapping("schedule")
    fun scheduleMetric(): MetricDto {
        val metric = nileMicrometer.scheduledMetric(
            name = "random_number",
            description = "Returns random number between 0 to 9",
            scrapeInterval = Duration.ofSeconds(5)
        ) {
            Random.nextDouble(10.0)
        }

        return with(metric) {
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

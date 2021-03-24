package tech.volkov.spring.boot.application.controller

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/metrics")
class SpringBootMetricsController(
    private val webClient: WebClient
) {

    data class CatFact(val fact: String, val length: Int)

    @GetMapping("timer")
    fun timerMetric(): CatFact? {
        val catFactResponse = webClient
                .get()
                .uri {
                    UriComponentsBuilder.fromHttpUrl("https://catfact.ninja")
                        .pathSegment("fact")
                        .build()
                        .toUri()
                }
                .exchangeToMono { it.toEntity(CatFact::class.java) }
                .block()

        return if (catFactResponse?.statusCode?.is2xxSuccessful == true) {
            logger.info { "Successfully received cat fact: [${catFactResponse.body?.fact}]" }
            catFactResponse.body
        } else {
            logger.error { "Failed to receive cat fact" }
            null
        }
    }

    companion object : KLogging()
}

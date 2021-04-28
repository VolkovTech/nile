package tech.volkov.nile.application.service

import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import tech.volkov.nile.application.dto.DogFact
import tech.volkov.nile.micrometer.metric.withTimer

@Service
class DogFactServiceImpl(
    private val webClient: WebClient
) : DogFactService {

    override fun getFactWithTimer(): List<String> {
        val dogFactsResponse = withTimer(
            metricName = "cat_fact",
            description = "Time to execute call to cat fact API"
        ) {
            getDogFacts()
        }

        return if (dogFactsResponse?.statusCode?.is2xxSuccessful == true) {
            logger.info { "Successfully received dog fact: [${dogFactsResponse.body?.facts}]" }
            dogFactsResponse.body.facts
        } else {
            logger.error { "Failed to receive dog fact" }
            emptyList()
        }
    }

    private fun getDogFacts() = webClient
        .get()
        .uri {
            UriComponentsBuilder.fromHttpUrl("https://dog-api.kinduff.com")
                .pathSegment("api/facts")
                .queryParam("number", 5)
                .build()
                .toUri()
        }
        .exchangeToMono { it.toEntity(DogFact::class.java) }
        .block()

    companion object : KLogging()
}

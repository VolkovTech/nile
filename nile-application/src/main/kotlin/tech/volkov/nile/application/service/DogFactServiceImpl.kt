package tech.volkov.nile.application.service

import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import tech.volkov.nile.application.dto.DogFact

@Service
class DogFactServiceImpl(
    private val webClient: WebClient
) : DogFactService {

    override fun getDogFacts(number: Int): List<String> {
        val dogFactsResponse = webClient
            .get()
            .uri {
                UriComponentsBuilder.fromHttpUrl("https://dog-api.kinduff.com")
                    .pathSegment("api/facts")
                    .queryParam("number", number)
                    .build()
                    .toUri()
            }
            .exchangeToMono { it.toEntity(DogFact::class.java) }
            .block()

        return if (dogFactsResponse?.statusCode?.is2xxSuccessful == true) {
            logger.info { "Successfully received dog fact: [${dogFactsResponse.body?.facts}]" }
            dogFactsResponse.body.facts
        } else {
            logger.error { "Failed to receive dog fact" }
            emptyList()
        }
    }

    companion object : KLogging()
}

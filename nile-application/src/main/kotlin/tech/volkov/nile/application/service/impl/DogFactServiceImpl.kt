@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package tech.volkov.nile.application.service.impl

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import tech.volkov.nile.application.dto.DogFact
import tech.volkov.nile.application.service.DogFactService

@Service
class DogFactServiceImpl(
    private val webClient: WebClient
) : DogFactService {

    override fun getFacts(number: Int): List<String> = webClient
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
        .body
        .facts
}

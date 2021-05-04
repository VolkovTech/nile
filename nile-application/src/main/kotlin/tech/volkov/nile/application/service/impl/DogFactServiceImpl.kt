@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package tech.volkov.nile.application.service.impl

import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import tech.volkov.nile.application.dto.DogFact
import tech.volkov.nile.application.service.DogFactService
import tech.volkov.nile.micrometer.metric.nileCounter
import tech.volkov.nile.micrometer.metric.nileDistributionSummary
import tech.volkov.nile.micrometer.metric.nileGauge
import tech.volkov.nile.micrometer.metric.nileTimer

@Service
class DogFactServiceImpl(
    private val webClient: WebClient
) : DogFactService {

    override fun getFacts(number: Int): List<String> {
        val dogFactsResponse = nileTimer(
            name = "dog_facts_timer",
            description = "Response time for dog facts API"
        ) {
            getDogFacts(number)
        }
        val isSuccess = dogFactsResponse.statusCode.is2xxSuccessful
        val dogFacts = dogFactsResponse.body.facts

        nileCounter(
            name = "dog_facts_counter",
            description = "Counts how many request were executed to dog facts API",
            isSuccess = isSuccess
        )

        nileGauge(
            name = "dog_facts_gauge",
            description = "Gauge for dogs facts"
        ) {
            dogFacts.size.toDouble()
        }

        nileDistributionSummary(
            name = "dog_facts_distribution_summary",
            unit = "short",
            description = "Distribution summary for dogs facts"
        ) {
            dogFacts.size.toDouble()
        }

        return if (isSuccess) {
            logger.info { "Successfully received dog fact: [$dogFacts]" }
            dogFacts
        } else {
            logger.error { "Failed to receive dog fact" }
            emptyList()
        }
    }

    private fun getDogFacts(number: Int) = webClient
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

    companion object : KLogging()
}

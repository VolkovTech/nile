package tech.volkov.nile.application.service.impl

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import tech.volkov.nile.application.configuration.properties.OpenWeatherProperties
import tech.volkov.nile.application.dto.CityWeather
import tech.volkov.nile.application.service.WeatherMonitoringService

/**
 * Service for monitoring weather conditions all around the world.
 */
@Service
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class WeatherMonitoringServiceImpl(
    private val webClient: WebClient,
    private val openWeatherProperties: OpenWeatherProperties
) : WeatherMonitoringService {

    override fun getCurrentWeather(city: String): CityWeather = webClient
        .get()
        .uri {
            UriComponentsBuilder.fromHttpUrl(openWeatherProperties.baseUrl)
                .queryParam("q", city)
                .queryParam("appid", openWeatherProperties.apiKey)
                .build()
                .toUri()
        }
        .exchangeToMono { it.toEntity(CityWeather::class.java) }
        .block()
        .body
}

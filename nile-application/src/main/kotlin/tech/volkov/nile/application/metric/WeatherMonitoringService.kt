package tech.volkov.nile.application.metric

import org.springframework.stereotype.Service
import tech.volkov.nile.application.configuration.properties.OpenWeatherProperties
import javax.annotation.PostConstruct

/**
 * Service for monitoring weather conditions all around the world.
 */
@Service
class WeatherMonitoringService(
    private val openWeatherProperties: OpenWeatherProperties
) {

    @PostConstruct
    fun postConstruct() {
        println("hello!")
    }
}

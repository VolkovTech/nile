package tech.volkov.nile.application.metric

import org.springframework.stereotype.Service
import tech.volkov.nile.application.configuration.properties.OpenWeatherProperties
import tech.volkov.nile.micrometer.annotation.NileScheduledMetric
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

    @NileScheduledMetric(metricName = "hello_world")
    fun updateHelloWorldMetric() {
        println("metric hello world was successfully updated")
    }
}

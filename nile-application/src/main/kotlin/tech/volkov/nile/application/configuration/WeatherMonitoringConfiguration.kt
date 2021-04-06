package tech.volkov.nile.application.configuration

import org.springframework.context.annotation.Configuration
import tech.volkov.nile.application.configuration.properties.OpenWeatherProperties
import tech.volkov.nile.application.service.WeatherService
import tech.volkov.nile.micrometer.annotation.NileScheduledMetric
import tech.volkov.nile.micrometer.metric.nileCounter
import tech.volkov.nile.micrometer.metric.nileDistributionSummary
import tech.volkov.nile.micrometer.metric.nileGauge
import tech.volkov.nile.micrometer.metric.nileTimer
import tech.volkov.nile.micrometer.scheduled.nileScheduled
import java.time.Duration

@Configuration
class WeatherMonitoringConfiguration(
    private val openWeatherProperties: OpenWeatherProperties,
    private val weatherService: WeatherService
) {

    @NileScheduledMetric
    fun temperature() = nileScheduled(
        name = "temperature",
        scrapeInterval = Duration.ofSeconds(5)
    ) {
        openWeatherProperties.cities
            .map {
                nileCounter(
                    name = "weather_requests_counter",
                    description = "How many requests were sent to weather API"
                ) {
                    nileTimer(
                        name = "weather_requests_timer",
                        description = "How long does it take to process single request"
                    ) {
                        weatherService.getCurrentWeather(it).also {
                            nileDistributionSummary(
                                name = "temperature_distribution_summary",
                                description = "Distribution summary of temperature in different cities"
                            ) {
                                it.main.temp
                            }
                        }
                    }
                }
            }
            .forEach {
                nileGauge(
                    name = "temperature",
                    description = "Celsius temperatures in cities all around the world",
                    tags = mapOf("city" to it.name)
                ) { it.main.temp }
                nileGauge(name = "feels_like", tags = mapOf("city" to it.name)) { it.main.feelsLike }
                nileGauge(name = "temperature_min", tags = mapOf("city" to it.name)) { it.main.tempMin }
                nileGauge(name = "temperature_max", tags = mapOf("city" to it.name)) { it.main.tempMax }

                nileGauge(name = "pressure", tags = mapOf("city" to it.name)) { it.main.pressure.toDouble() }
                nileGauge(
                    name = "humidity",
                    description = "Humidity for particular city in percents",
                    tags = mapOf("city" to it.name)
                ) { it.main.humidity.toDouble() }

                nileGauge(name = "wind_speed", tags = mapOf("city" to it.name)) { it.wind.speed }
                nileGauge(name = "visibility", tags = mapOf("city" to it.name)) { it.visibility.toDouble() }
            }
    }
}

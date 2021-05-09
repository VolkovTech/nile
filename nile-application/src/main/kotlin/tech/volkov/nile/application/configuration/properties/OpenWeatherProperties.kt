package tech.volkov.nile.application.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("weather")
class OpenWeatherProperties {
    /**
     * Base url for making API calls
     */
    lateinit var baseUrl: String
    /**
     * API key retrieved from https://home.openweathermap.org/api_keys
     */
    lateinit var apiKey: String

    /**
     * Temperature is available in Fahrenheit, Celsius and Kelvin units.
     *
     * For temperature in Fahrenheit use units=imperial
     * For temperature in Celsius use units=metric
     * Temperature in Kelvin is used by default, no need to use units parameter in API call
     */
    var units: String = "metric"

    /**
     * There is a requests limit set by open weather map API.
     * So, the update time for each city will depend on this property.
     */
    var requestsLimitPerMinute: Int = 60

    /**
     * List of available cities
     */
    var cities: List<String> = emptyList()

    /**
     * Update interval for each metric, calculates as max number of requests allowed to call the API.
     */
    val updateInterval: Int
        get() = SEC_IN_MINUTE / (requestsLimitPerMinute / cities.size)

    companion object {
        private const val SEC_IN_MINUTE = 60
    }
}

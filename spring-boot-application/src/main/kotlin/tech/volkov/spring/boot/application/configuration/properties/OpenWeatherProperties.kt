package tech.volkov.spring.boot.application.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("weather")
class OpenWeatherProperties {
    /**
     * API key retrieved from https://home.openweathermap.org/api_keys
     */
    lateinit var apiKey: String

    /**
     * There is a requests limit set by open weather map API.
     * So, the update time for each city will depend on this property.
     */
    var requestsLimitPerMinute: Int = 60

    /**
     * Map represents country name against list of cities
     */
    var countriesCitiesMap: Map<String, List<String>> = emptyMap()

    /**
     * Update interval for each metric, calculates as max number of requests allowed to call the API.
     */
    val updateInterval: Int
        get() = SEC_IN_MINUTE / (requestsLimitPerMinute / countriesCitiesMap.values.flatten().size)

    companion object {
        private const val SEC_IN_MINUTE = 60
    }
}

package tech.volkov.nile.application.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CityWeather(
    val name: String,
    val weather: List<Weather>,
    val main: Main,
    val visibility: Int,
    val wind: Wind
) {
    data class Weather(
        val main: String,
        val description: String
    )

    data class Main(
        val temp: Double,
        @JsonProperty("feels_like")
        val feelsLike: Double,
        @JsonProperty("temp_min")
        val tempMin: Double,
        @JsonProperty("temp_max")
        val tempMax: Double,
        val pressure: Int,
        val humidity: Int
    )

    data class Wind(
        val speed: Double,
        val deg: Int
    )
}

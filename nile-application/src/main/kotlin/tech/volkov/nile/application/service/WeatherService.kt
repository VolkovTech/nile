package tech.volkov.nile.application.service

import tech.volkov.nile.application.dto.CityWeather

interface WeatherService {

    fun getCurrentWeather(city: String): CityWeather
}

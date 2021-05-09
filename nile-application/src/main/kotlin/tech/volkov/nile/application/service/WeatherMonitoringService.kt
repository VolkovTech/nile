package tech.volkov.nile.application.service

import tech.volkov.nile.application.dto.CityWeather

interface WeatherMonitoringService {

    fun getCurrentWeather(city: String): CityWeather
}

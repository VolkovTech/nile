package tech.volkov.nile.application.service

interface MockApiService {

    /**
     * Returns random number with random delay
     */
    fun getNumber(): Double
}

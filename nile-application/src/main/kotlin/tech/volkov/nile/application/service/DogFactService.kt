package tech.volkov.nile.application.service

import kotlin.random.Random

interface DogFactService {

    /**
     * Returns random number of dog facts by calling dog facts API
     */
    fun getFacts(number: Int = Random.nextInt(10)): List<String>
}

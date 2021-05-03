package tech.volkov.nile.application.service

interface DogFactService {

    fun getDogFacts(number: Int = 5): List<String>
}

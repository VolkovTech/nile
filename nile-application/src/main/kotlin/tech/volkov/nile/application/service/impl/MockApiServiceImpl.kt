@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package tech.volkov.nile.application.service.impl

import org.springframework.stereotype.Service
import tech.volkov.nile.application.service.MockApiService
import kotlin.random.Random

@Service
class MockApiServiceImpl : MockApiService {

    override fun getNumber(): Double {
        // mock API call
        Thread.sleep(Random.nextLong(MAX_DELAY_IN_MS))
        return Random.nextDouble(MAX_RANDOM_RANGE)
    }

    companion object {
        private const val MAX_DELAY_IN_MS = 500L
        private const val MAX_RANDOM_RANGE = 100.0
    }
}

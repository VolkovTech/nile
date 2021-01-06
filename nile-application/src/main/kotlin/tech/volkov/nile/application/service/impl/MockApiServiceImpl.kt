@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package tech.volkov.nile.application.service.impl

import org.springframework.stereotype.Service
import tech.volkov.nile.application.service.MockApiService
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import kotlin.random.Random

@Service
class MockApiServiceImpl : MockApiService {

    val startAnomaly: LocalDateTime = now().plusMinutes(4)
    val endAnomaly: LocalDateTime = now().plusMinutes(6)

    override fun getNumber(): Double {
        // mock API call
        if (now().isAfter(startAnomaly) && now().isBefore(endAnomaly)) {
            Thread.sleep(2000)
        }
        Thread.sleep(Random.nextLong(MAX_DELAY_IN_MS))
        return Random.nextDouble(MAX_RANDOM_RANGE)
    }

    companion object {
        private const val MAX_DELAY_IN_MS = 500L
        private const val MAX_RANDOM_RANGE = 100.0
    }
}

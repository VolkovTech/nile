package tech.volkov.nile.application.micrometer.metric

import org.junit.jupiter.api.Test

internal class NileCounterTest {

    @Test
    fun `counter correctly increments`() {
//        registry.find(TEST_COUNTER_NAME).counters() shouldHaveSize 0
//
//        nileCounter(TEST_COUNTER_NAME)
//
//        registry.find(TEST_COUNTER_NAME).counters().asClue {
//            it shouldHaveSize 1
//            it.first().id.name shouldBe TEST_COUNTER_NAME
//        }
    }

    companion object {
        private const val TEST_COUNTER_NAME = "test_counter"
    }
}

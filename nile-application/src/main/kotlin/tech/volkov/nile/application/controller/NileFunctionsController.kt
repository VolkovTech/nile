package tech.volkov.nile.application.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.application.service.MockApiService
import tech.volkov.nile.micrometer.metric.nileCounter
import tech.volkov.nile.micrometer.metric.nileDistributionSummary
import tech.volkov.nile.micrometer.metric.nileGauge
import tech.volkov.nile.micrometer.metric.nileTimer

@RestController
@RequestMapping("api/function")
class NileFunctionsController(
    private val mockApiService: MockApiService
) {

    @GetMapping("counter")
    fun counter() = nileCounter(
        name = "api_counter",
        description = "Counts how many request were executed to API",
        amount = 10.0
    ) {
        mockApiService.getNumber()
    }

    @GetMapping("timer")
    fun timer() = nileTimer(
        name = "api_timer",
        description = "Response time for external API",
        percentiles = listOf(0.5, 0.75, 0.9, 0.95, 0.99)
    ) {
        mockApiService.getNumber()
    }

    @GetMapping("gauge")
    fun gauge() = nileGauge(
        name = "api_gauge",
        description = "Current value of value returned from external API",
    ) {
        mockApiService.getNumber()
    }

    @GetMapping("distribution-summary")
    fun distributionSummary() = nileDistributionSummary(
        name = "api_distribution_summary",
        unit = "short",
        description = "Distribution summary for API requests"
    ) {
        mockApiService.getNumber()
    }
}

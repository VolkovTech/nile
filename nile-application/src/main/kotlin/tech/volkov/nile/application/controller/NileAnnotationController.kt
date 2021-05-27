package tech.volkov.nile.application.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.volkov.nile.application.service.MockApiService
import tech.volkov.nile.micrometer.annotation.NileCounter
import tech.volkov.nile.micrometer.annotation.NileDistributionSummary
import tech.volkov.nile.micrometer.annotation.NileGauge
import tech.volkov.nile.micrometer.annotation.NileTimer

@RestController
@RequestMapping("api/annotation")
class NileAnnotationController(
    private val mockApiService: MockApiService
) {

    @GetMapping
    @NileCounter(
        name = "api_counter_annotation",
        description = "Counts how many request were executed to external API",
        tags = ["annotation", "true"],
        amount = 10.0
    )
    @NileTimer(
        name = "api_timer_annotation",
        description = "Response time for external API",
        tags = ["annotation", "true"],
        percentiles = [0.5, 0.75, 0.9, 0.95, 0.99]
    )
    @NileGauge(
        name = "api_gauge_annotation",
        description = "Current value of response from external API",
        tags = ["annotation", "true"]
    )
    @NileDistributionSummary(
        name = "api_distribution_summary_annotation",
        unit = "short",
        description = "Distribution summary for external API",
        tags = ["annotation", "true"],
        percentiles = [0.5, 0.75, 0.9, 0.95, 0.99]
    )
    fun getDogFacts() = mockApiService.getNumber()
}

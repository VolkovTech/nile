package tech.volkov.nile.application.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.volkov.nile.application.service.MockApiService
import tech.volkov.nile.micrometer.annotation.NileScheduledMetric
import tech.volkov.nile.micrometer.scheduled.nileCounterScheduled
import tech.volkov.nile.micrometer.scheduled.nileDistributionSummaryScheduled
import tech.volkov.nile.micrometer.scheduled.nileGaugeScheduled
import tech.volkov.nile.micrometer.scheduled.nileTimerScheduled
import tech.volkov.nile.micrometer.scheduler.NileScheduler
import java.time.Duration
import kotlin.math.sin

@Configuration
class NileConfiguration(
    private val mockApiService: MockApiService
) {

    @Bean
    fun nileScheduler() = NileScheduler.builder()
        .corePoolSize(5)
        .maximumPoolSize(10)
        .keepAliveTime(5000)
        .queueCapacity(10)
        .defaultScrapeInterval(Duration.ofSeconds(30))
        .build()

    @NileScheduledMetric
    fun counter() = nileCounterScheduled(
        "api_counter_scheduled",
        scrapeInterval = Duration.ofSeconds(5)
    ) {
        mockApiService.getNumber()
    }

    @NileScheduledMetric
    fun timer() = nileTimerScheduled("api_timer_scheduled") {
        mockApiService.getNumber()
    }

    @NileScheduledMetric
    fun gauge() = nileGaugeScheduled("api_gauge_scheduled") {
        mockApiService.getNumber()
    }

    @NileScheduledMetric
    fun distributionSummary() = nileDistributionSummaryScheduled("api_distribution_summary_scheduled") {
        mockApiService.getNumber()
    }

    private var x = 0.0

    @NileScheduledMetric
    fun sin() = nileGaugeScheduled("sin", scrapeInterval = Duration.ofSeconds(15)) {
        sin(x).also { x += 0.25 }
    }
}

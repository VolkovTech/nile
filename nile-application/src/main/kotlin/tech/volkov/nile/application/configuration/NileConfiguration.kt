package tech.volkov.nile.application.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.volkov.nile.application.service.DogFactService
import tech.volkov.nile.micrometer.annotation.NileScheduledMetric
import tech.volkov.nile.micrometer.scheduled.nileCounterScheduled
import tech.volkov.nile.micrometer.scheduled.nileDistributionSummaryScheduled
import tech.volkov.nile.micrometer.scheduled.nileGaugeScheduled
import tech.volkov.nile.micrometer.scheduled.nileTimerScheduled
import tech.volkov.nile.micrometer.scheduler.NileScheduler
import java.time.Duration
import kotlin.random.Random

@Configuration
class NileConfiguration(
    private val dogFactService: DogFactService
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
        "dog_facts_counter_scheduled",
        scrapeInterval = Duration.ofSeconds(5)
    ) {
        Random.nextDouble(10.0)
    }

    @NileScheduledMetric
    fun timer() = nileTimerScheduled("dog_facts_timer_scheduled") {
        dogFactService.getFacts()
    }

    @NileScheduledMetric
    fun gauge() = nileGaugeScheduled("dog_facts_timer_scheduled") {
        Random.nextDouble(100.0)
    }

    @NileScheduledMetric
    fun distributionSummary() = nileDistributionSummaryScheduled("dog_facts_distribution_summary_scheduled") {
        Random.nextDouble(100.0)
    }
}

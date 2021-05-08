package tech.volkov.nile.micrometer.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.volkov.nile.micrometer.aspect.basic.NileCounterAspect
import tech.volkov.nile.micrometer.aspect.basic.NileDistributionSummaryAspect
import tech.volkov.nile.micrometer.aspect.basic.NileGaugeAspect
import tech.volkov.nile.micrometer.aspect.basic.NileTimerAspect

@Configuration
class NileAutoConfiguration {

    @Bean
    fun nileCounterAspect() = NileCounterAspect()

    @Bean
    fun nileTimerAspect() = NileTimerAspect()

    @Bean
    fun nileGaugeAspect() = NileGaugeAspect()

    @Bean
    fun nileDistributionSummaryAspect() = NileDistributionSummaryAspect()
}

package tech.volkov.nile.micrometer.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.volkov.nile.micrometer.annotation.aspect.NileCounterAspect

@Configuration
class NileAutoConfiguration {

    @Bean
    fun nileCounterAspect() = NileCounterAspect()
}

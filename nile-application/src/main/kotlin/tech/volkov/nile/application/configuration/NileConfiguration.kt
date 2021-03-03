package tech.volkov.nile.application.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.volkov.nile.micrometer.builder.Nile

@Configuration
class NileConfiguration {

    @Bean
    fun nile() = Nile.builder().build()
}

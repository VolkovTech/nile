package tech.volkov.nile.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.volkov.nilecore.builder.Nile

@Configuration
class NileConfiguration {

    @Bean
    fun nile() = Nile.builder().build()
}

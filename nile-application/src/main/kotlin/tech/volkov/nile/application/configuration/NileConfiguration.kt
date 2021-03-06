package tech.volkov.nile.application.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.volkov.nile.micrometer.builder.Nile
import java.time.Duration

@Configuration
class NileConfiguration {

    @Bean
    fun nile() = Nile.builder()
        .corePoolSize(1)
        .defaultScrapeInterval(Duration.ofSeconds(30))
        .build()
}

package tech.volkov.nile.micrometer.configuration

import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
abstract class NileConfiguration {

    @PostConstruct
    abstract fun configure()
}

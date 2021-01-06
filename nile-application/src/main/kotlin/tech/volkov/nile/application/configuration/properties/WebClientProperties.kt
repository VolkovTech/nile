package tech.volkov.nile.application.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("web-client")
class WebClientProperties {
    var connectTimeout: Int = 1000
    var readTimeout: Long = 5000
    var writeTimeout: Long = 5000
}

package tech.volkov.nile.application

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
    exclude = [DataSourceAutoConfiguration::class]
)
class NileApplication

fun main(args: Array<String>) {
    runApplication<NileApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}

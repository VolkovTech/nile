package tech.volkov.spring.boot.application.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.volkov.spring.boot.application.NileApplication

@Configuration
class OpenApiConfiguration {

    @Bean
    fun groupedOpenApi(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("public")
        .packagesToScan(NileApplication::class.java.`package`.name)
        .build()

    @Bean
    fun openAPI(): OpenAPI = OpenAPI().info(
        Info()
            .title(NileApplication::class.java.simpleName)
    )
}

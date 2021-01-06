plugins {
    kotlin("jvm")
    kotlin("plugin.spring")

    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // webclient
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-webflux")
    implementation(group = "io.projectreactor.kotlin", name = "reactor-kotlin-extensions", version = "1.1.2")

    // open api
    implementation(group = "org.springdoc", name = "springdoc-openapi-ui", version = "1.5.5")

    // micrometer
    runtimeOnly("io.micrometer:micrometer-registry-prometheus:1.6.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

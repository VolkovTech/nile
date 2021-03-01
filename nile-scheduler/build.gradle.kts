tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")

    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":nile-core"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus:1.6.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

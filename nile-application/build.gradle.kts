plugins {
    kotlin("jvm")
    kotlin("plugin.spring")

    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    api(project(":nile-micrometer"))
    api(project(":nile-grafana"))
    api(project(":nile-anomaly"))
}

plugins {
    kotlin("jvm")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // metrics
    implementation("io.micrometer:micrometer-core:1.6.2")

    // logger
    implementation("io.github.microutils:kotlin-logging:2.0.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.1")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

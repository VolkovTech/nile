tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // logging
    implementation("io.github.microutils:kotlin-logging:2.0.4")
    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.1")
    // metrics
    implementation("io.micrometer:micrometer-core:1.6.2")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

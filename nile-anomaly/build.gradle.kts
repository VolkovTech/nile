tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

dependencies {
    implementation(project(":nile-grafana"))

    implementation(group = "org.nield", name = "kotlin-statistics", version = "1.2.1")
}

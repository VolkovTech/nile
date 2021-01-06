package tech.volkov.spring.boot.application.dto

import java.time.Duration

data class MetricDto(
    val name: String,
    val description: String,
    val scrapeInterval: Duration?,
    val sampleValue: Double?
)

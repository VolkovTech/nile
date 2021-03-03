package tech.volkov.nile.application.dto

data class MetricDto(
    val name: String,
    val description: String,
    val scrapeInterval: Long,
    val sampleValue: Double
)

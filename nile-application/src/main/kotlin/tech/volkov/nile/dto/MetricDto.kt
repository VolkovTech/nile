package tech.volkov.nile.dto

data class MetricDto(
    val name: String,
    val description: String,
    val scrapeInterval: Int,
    val sampleValue: Double
)

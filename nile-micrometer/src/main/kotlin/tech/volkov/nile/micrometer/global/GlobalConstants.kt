package tech.volkov.nile.micrometer.global

internal const val STATUS_TAG_NAME = "status"
internal const val STATUS_SUCCESS = "SUCCESS"
internal const val STATUS_FAILURE = "FAILURE"

internal val DEFAULT_PERCENTILES = listOf(0.5, 0.75, 0.9, 0.95, 0.99)

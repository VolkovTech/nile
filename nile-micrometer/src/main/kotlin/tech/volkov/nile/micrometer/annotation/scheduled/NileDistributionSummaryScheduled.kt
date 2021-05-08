package tech.volkov.nile.micrometer.annotation.scheduled

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class NileDistributionSummaryScheduled(
    val name: String,
    val unit: String = "",
    val description: String = "",
    val tags: Array<String> = [],
    val percentiles: DoubleArray = [0.5, 0.75, 0.9, 0.95, 0.99],
    val scrapeInterval: String = "PT30S"
)

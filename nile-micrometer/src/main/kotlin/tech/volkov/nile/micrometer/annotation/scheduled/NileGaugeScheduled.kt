package tech.volkov.nile.micrometer.annotation.scheduled

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class NileGaugeScheduled(
    val name: String,
    val description: String = "",
    val tags: Array<String> = [],
    val scrapeInterval: String = "PT30S"
)

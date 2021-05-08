package tech.volkov.nile.micrometer.annotation.scheduled

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class NileCounterScheduled(
    val name: String,
    val description: String = "",
    val tags: Array<String> = [],
    val amount: Double = 1.0,
    val scrapeInterval: String = "PT30S"
)

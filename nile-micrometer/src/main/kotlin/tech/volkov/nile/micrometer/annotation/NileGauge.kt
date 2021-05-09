package tech.volkov.nile.micrometer.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NileGauge(
    val name: String,
    val description: String = "",
    val tags: Array<String> = []
)

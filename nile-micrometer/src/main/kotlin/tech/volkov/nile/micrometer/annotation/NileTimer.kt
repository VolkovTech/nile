package tech.volkov.nile.micrometer.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NileTimer(
    val name: String,
    val description: String = "",
    val tags: Array<String> = [],
    val percentiles: DoubleArray = [0.5, 0.75, 0.9, 0.95, 0.99]
)

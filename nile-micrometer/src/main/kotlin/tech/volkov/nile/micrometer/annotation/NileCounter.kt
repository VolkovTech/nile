package tech.volkov.nile.micrometer.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NileCounter(
    val name: String,
    val description: String = "",
    val tags: Array<String> = [],
    val amount: Double = 1.0
)

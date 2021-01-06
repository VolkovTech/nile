package tech.volkov.nile.micrometer.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class NileScheduledMetric(
    val metricName: String
)

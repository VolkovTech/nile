package tech.volkov.nile.micrometer.annotation.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import tech.volkov.nile.micrometer.annotation.metric.NileGauge
import tech.volkov.nile.micrometer.global.getTags
import tech.volkov.nile.micrometer.metric.nileGauge

@Aspect
class NileGaugeAspect {

    @Around("@annotation(tech.volkov.nile.micrometer.annotation.metric.NileGauge)")
    fun around(joinPoint: ProceedingJoinPoint): Any {
        val method = joinPoint.target.javaClass.getMethod(joinPoint.signature.name)
        val gauge = method.getAnnotation(NileGauge::class.java)

        try {
            val result = joinPoint.proceed()
            nileGauge(gauge.name, gauge.description, getTags(gauge.tags), true) {
                (result as? Double) ?: 0.0
            }
            return result
        } catch (ex: Exception) {
            nileGauge(gauge.name, gauge.description, getTags(gauge.tags), false)
            throw ex
        }
    }
}

package tech.volkov.nile.micrometer.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import tech.volkov.nile.micrometer.annotation.NileGauge
import tech.volkov.nile.micrometer.global.getTags
import tech.volkov.nile.micrometer.metric.nileGauge

@Aspect
class NileGaugeAspect {

    @Around("@annotation(tech.volkov.nile.micrometer.annotation.NileGauge)")
    fun around(joinPoint: ProceedingJoinPoint): Any {
        val method = joinPoint.target.javaClass.getMethod(joinPoint.signature.name)
        val gauge = method.getAnnotation(NileGauge::class.java)

        return nileGauge(gauge.name, gauge.description, getTags(gauge.tags)) {
            (joinPoint.proceed() as? Double) ?: 0.0
        }
    }
}

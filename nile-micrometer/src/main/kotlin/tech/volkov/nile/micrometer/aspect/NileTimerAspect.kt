package tech.volkov.nile.micrometer.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import tech.volkov.nile.micrometer.annotation.NileTimer
import tech.volkov.nile.micrometer.global.getTags
import tech.volkov.nile.micrometer.metric.nileTimer

@Aspect
class NileTimerAspect {

    @Around("@annotation(tech.volkov.nile.micrometer.annotation.NileTimer)")
    fun around(joinPoint: ProceedingJoinPoint): Any {
        val method = joinPoint.target.javaClass.getMethod(joinPoint.signature.name)
        val timer = method.getAnnotation(NileTimer::class.java)

        return nileTimer(timer.name, timer.description, getTags(timer.tags), timer.percentiles.toList()) {
            joinPoint.proceed()
        }
    }
}

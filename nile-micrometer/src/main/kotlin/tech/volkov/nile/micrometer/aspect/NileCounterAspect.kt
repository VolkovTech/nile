package tech.volkov.nile.micrometer.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import tech.volkov.nile.micrometer.annotation.NileCounter
import tech.volkov.nile.micrometer.global.getTags
import tech.volkov.nile.micrometer.metric.nileCounter

@Aspect
class NileCounterAspect {

    @Around("@annotation(tech.volkov.nile.micrometer.annotation.NileCounter)")
    fun around(joinPoint: ProceedingJoinPoint): Any {
        val method = joinPoint.target.javaClass.getMethod(joinPoint.signature.name)
        val counter = method.getAnnotation(NileCounter::class.java)

        return nileCounter(counter.name, counter.description, getTags(counter.tags), counter.amount) {
            joinPoint.proceed()
        }
    }
}

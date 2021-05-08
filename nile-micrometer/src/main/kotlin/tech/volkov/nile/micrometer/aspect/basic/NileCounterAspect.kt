package tech.volkov.nile.micrometer.aspect.basic

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import tech.volkov.nile.micrometer.annotation.basic.NileCounter
import tech.volkov.nile.micrometer.global.getTags
import tech.volkov.nile.micrometer.metric.nileCounter

@Aspect
class NileCounterAspect {

    @Around("@annotation(tech.volkov.nile.micrometer.annotation.basic.NileCounter)")
    fun around(joinPoint: ProceedingJoinPoint): Any {
        val method = joinPoint.target.javaClass.getMethod(joinPoint.signature.name)
        val counter = method.getAnnotation(NileCounter::class.java)

        try {
            val result = joinPoint.proceed()
            nileCounter(counter.name, counter.description, getTags(counter.tags), true) { counter.amount }
            return result
        } catch (ex: Exception) {
            nileCounter(counter.name, counter.description, getTags(counter.tags), false) { counter.amount }
            throw ex
        }
    }
}

package tech.volkov.nile.micrometer.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import tech.volkov.nile.micrometer.annotation.NileDistributionSummary
import tech.volkov.nile.micrometer.metric.nileDistributionSummary
import tech.volkov.nile.micrometer.util.getTags

@Aspect
class NileDistributionSummaryAspect {

    @Around("@annotation(tech.volkov.nile.micrometer.annotation.NileDistributionSummary)")
    fun around(joinPoint: ProceedingJoinPoint): Any {
        val method = joinPoint.target.javaClass.getMethod(joinPoint.signature.name)
        val ds = method.getAnnotation(NileDistributionSummary::class.java)

        return nileDistributionSummary(ds.name, ds.unit, ds.description, getTags(ds.tags), ds.percentiles.toList()) {
            (joinPoint.proceed() as? Double) ?: 0.0
        }
    }
}

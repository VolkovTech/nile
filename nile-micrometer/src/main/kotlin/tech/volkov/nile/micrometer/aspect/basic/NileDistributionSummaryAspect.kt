package tech.volkov.nile.micrometer.aspect.basic

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import tech.volkov.nile.micrometer.annotation.basic.NileDistributionSummary
import tech.volkov.nile.micrometer.global.getTags
import tech.volkov.nile.micrometer.metric.nileDistributionSummary

@Aspect
class NileDistributionSummaryAspect {

    @Around("@annotation(tech.volkov.nile.micrometer.annotation.basic.NileDistributionSummary)")
    fun around(joinPoint: ProceedingJoinPoint): Any {
        val method = joinPoint.target.javaClass.getMethod(joinPoint.signature.name)
        val ds = method.getAnnotation(NileDistributionSummary::class.java)
        val percentiles = ds.percentiles.toList()

        try {
            val result = joinPoint.proceed()
            nileDistributionSummary(ds.name, ds.unit, ds.description, getTags(ds.tags), percentiles, true) {
                (result as? Double) ?: 0.0
            }
            return result
        } catch (ex: Exception) {
            nileDistributionSummary(ds.name, ds.unit, ds.description, getTags(ds.tags), percentiles, false) {
                0.0
            }
            throw ex
        }
    }
}

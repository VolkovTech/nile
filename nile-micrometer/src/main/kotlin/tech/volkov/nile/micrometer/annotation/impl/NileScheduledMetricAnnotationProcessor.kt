package tech.volkov.nile.micrometer.annotation.impl

import com.google.auto.service.AutoService
import tech.volkov.nile.micrometer.annotation.NileScheduledMetric
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class NileScheduledMetricAnnotationProcessor : AbstractProcessor() {

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(NileScheduledMetric::class.java.name)
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val elementsWithMyConst = roundEnv?.getElementsAnnotatedWith(NileScheduledMetric::class.java)
        println(elementsWithMyConst)

        return true
    }
}

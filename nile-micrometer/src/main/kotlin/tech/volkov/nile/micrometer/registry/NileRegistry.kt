package tech.volkov.nile.micrometer.registry

import tech.volkov.nile.micrometer.context.MetricContext

internal class NileRegistry private constructor() {

    companion object {
        var globalRegistry = mutableMapOf<String, MetricContext>()

        fun clear() {
            globalRegistry = mutableMapOf()
        }
    }
}

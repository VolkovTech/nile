package tech.volkov.nile.micrometer.registry

import tech.volkov.nile.micrometer.model.NileScheduledTask

internal class NileScheduledRegistry private constructor() {

    companion object {
        var globalRegistry = mutableMapOf<String, NileScheduledTask>()
            private set

        fun addScheduledTask(name: String, block: NileScheduledTask.() -> Unit) = NileScheduledTask(name)
            .apply(block)
            .also { globalRegistry[name] = it }
    }
}

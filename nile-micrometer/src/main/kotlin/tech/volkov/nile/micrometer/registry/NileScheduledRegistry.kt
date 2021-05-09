package tech.volkov.nile.micrometer.registry

import tech.volkov.nile.micrometer.context.ScheduledTask

internal class NileScheduledRegistry private constructor() {

    companion object {
        var globalRegistry = mutableMapOf<String, ScheduledTask>()
            private set

        fun addScheduledTask(name: String, block: ScheduledTask.() -> Unit) {
            globalRegistry[name] = ScheduledTask(name).apply(block)
        }

        fun clear() {
            globalRegistry = mutableMapOf()
        }
    }
}

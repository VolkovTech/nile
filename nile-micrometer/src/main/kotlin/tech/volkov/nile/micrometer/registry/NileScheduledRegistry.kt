package tech.volkov.nile.micrometer.registry

internal class NileScheduledRegistry private constructor() {

    companion object {
        var globalRegistry = mutableMapOf<String, NileScheduledTask>()
            private set

        fun addScheduledTask(name: String, block: NileScheduledTask.() -> Unit) {
            globalRegistry[name] = NileScheduledTask(name).apply(block)
        }

        fun clear() {
            globalRegistry = mutableMapOf()
        }
    }
}

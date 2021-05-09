package tech.volkov.nile.micrometer.scheduled

import tech.volkov.nile.micrometer.metric.nileCounter
import tech.volkov.nile.micrometer.registry.NileScheduledRegistry.Companion.addScheduledTask
import java.time.Duration

fun <T> nileCounterScheduled(
    name: String,
    description: String = "",
    tags: Map<String, String> = mapOf(),
    amount: Double = 1.0,
    scrapeInterval: Duration = Duration.ofMillis(30),
    block: () -> T
) = addScheduledTask(name) {
    this.scrapeInterval = scrapeInterval
    this.block = { nileCounter(name, description, tags, amount) { block } }
}

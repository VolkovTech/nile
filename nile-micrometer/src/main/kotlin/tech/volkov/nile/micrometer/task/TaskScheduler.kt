package tech.volkov.nile.micrometer.task

import kotlinx.coroutines.launch
import mu.KLogging
import tech.volkov.nile.micrometer.registry.NileScheduledTask
import tech.volkov.nile.micrometer.executor.NileExecutor
import tech.volkov.nile.micrometer.registry.NileScheduledRegistry.Companion.globalRegistry
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class TaskScheduler(
    private val nileExecutor: NileExecutor
) {

    init {
        nileExecutor.scheduledExecutorService.scheduleAtFixedRate(
            { runUpdateMetrics() },
            0,
            UPDATE_PERIOD_IN_MILLIS,
            TimeUnit.MILLISECONDS
        )
    }

    private fun runUpdateMetrics() = globalRegistry.forEach {
        nileExecutor.coroutineScope.launch { updateMetricIfNeeded(it.value) }
    }

    private fun updateMetricIfNeeded(nileScheduledTask: NileScheduledTask) = with(nileScheduledTask) {
        if (nextScrapeTime.isBefore(LocalDateTime.now())) {
            runCatching(block)
                .also { logger.debug { "Updated metric [$name]" } }
                .also { updateNextScrapeTime() }
                .getOrThrow()
        }
    }

    companion object : KLogging() {
        private const val UPDATE_PERIOD_IN_MILLIS = 1000L
    }
}

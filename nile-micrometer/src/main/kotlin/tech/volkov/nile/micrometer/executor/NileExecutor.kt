package tech.volkov.nile.micrometer.executor

import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class NileExecutor(
    corePoolSize: Int,
    maximumPoolSize: Int,
    keepAliveTime: Long,
    queueCapacity: Int
) {

    private val threadPoolExecutor = ThreadPoolExecutor(
        corePoolSize,
        maximumPoolSize,
        keepAliveTime,
        TimeUnit.SECONDS,
        LinkedBlockingQueue(queueCapacity)
    )
        .also { ExecutorServiceMetrics(it, "nileThreadPoolExecutor", listOf()) }

    val scheduledExecutorService: ScheduledExecutorService = Executors.newScheduledThreadPool(SINGLE_THREAD_POOL_SIZE)
    val coroutineScope = CoroutineScope(threadPoolExecutor.asCoroutineDispatcher() + SupervisorJob())

    companion object {
        private const val SINGLE_THREAD_POOL_SIZE = 1
    }
}

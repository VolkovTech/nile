package tech.volkov.nilescheduler.configuration

import mu.KLogging
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Component

@Configuration
class TaskSchedulerConfiguration(
    private val nileThreadPoolProperties: NileThreadPoolProperties
) {

    @Bean
    fun nileTaskScheduler() = with(nileThreadPoolProperties) {
        ThreadPoolTaskScheduler().also {
            it.setThreadNamePrefix("nile-task-scheduler-thread-")
            it.poolSize = poolSize
            it.isDaemon = isDeamon
            it.initialize()
        }
    }

    companion object : KLogging()
}

@Component
@ConfigurationProperties("nile.thread-pool")
class NileThreadPoolProperties {
    var poolSize: Int = 10
    var isDeamon: Boolean = true
}

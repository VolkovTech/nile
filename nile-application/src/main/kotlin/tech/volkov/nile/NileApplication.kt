package tech.volkov.nile

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NileApplication

fun main(args: Array<String>) {
	runApplication<NileApplication>(*args)
}

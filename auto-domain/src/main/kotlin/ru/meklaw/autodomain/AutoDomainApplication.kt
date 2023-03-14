package ru.meklaw.autodomain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AutoDomainApplication

fun main(args: Array<String>) {
    runApplication<AutoDomainApplication>(*args)
}

package com.travel.point

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class PointApplication

fun main(args: Array<String>) {
    runApplication<PointApplication>(*args)
}

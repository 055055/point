package com.travel.point.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.DependsOn
import org.springframework.jdbc.core.JdbcTemplate
import org.testcontainers.containers.MySQLContainer
import javax.sql.DataSource

@TestConfiguration
class MySQLTestConfig {


    companion object {
        const val IMAGE_NAME = "mysql:5.7"
        const val DATABASE_NAME = "test-travel-point"
        const val PORT = 3306
    }

    @Bean
    fun mySqlDocker() = MySQLDocker()

    @Bean
    @DependsOn("mySqlDocker")
    fun dataSource(): DataSource =
        DataSourceBuilder.create()
            .url("""jdbc:mysql://localhost:${mySqlDocker().mysql.getMappedPort(PORT)}/$DATABASE_NAME?serverTimezone=Asia/Seoul""")
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .username("root")
            .password("password")
            .build()

    inner class MySQLDocker {
        val mysql = MySQLContainer<Nothing>(IMAGE_NAME).apply {
            startupAttempts = 1
            withUsername("root")
            withDatabaseName(DATABASE_NAME)
            withPassword("password")
            withUrlParam("useUnicode", "true")
            withUrlParam("characterEncoding", "utf-8")
            withUrlParam("connectionCollation", "utf8_bin")
            withUrlParam("characterSetResults", "utf8")
            withUrlParam("serverTimezone", "Asia/Seoul")
            withUrlParam("zeroDateTimeBehavior", "convertToNull")
            withReuse(true)
        }

        init {
            mysql.start()
        }

//        fun deleteAll() =

    }
}

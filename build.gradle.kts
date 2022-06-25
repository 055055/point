import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
}

group = "com.travel"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11


object Version{
    object Test{
        const val KOTEST = "5.3.1"
        const val MOCKK = "1.12.4"
        const val NINJA_SQUAD = "3.1.1"
        const val TESTCONTAINERS = "1.17.2"
    }
}

repositories {
    mavenCentral()
}

extra["testcontainersVersion"] = "1.17.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("mysql:mysql-connector-java")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("io.kotest:kotest-assertions-core:${Version.Test.KOTEST}")
    testImplementation ("io.kotest:kotest-runner-junit5:${Version.Test.KOTEST}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.0")
    testImplementation ("io.mockk:mockk:${Version.Test.MOCKK}")
    testImplementation ("com.ninja-squad:springmockk:${Version.Test.NINJA_SQUAD}")
    testImplementation ("org.testcontainers:testcontainers:${Version.Test.TESTCONTAINERS}")
    testImplementation("org.testcontainers:mysql:${Version.Test.TESTCONTAINERS}")

}



tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

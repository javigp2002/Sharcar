val kotlin_version: String by project
val logback_version: String by project
val ktor_version: String by project
val junit_version: String = "5.8.1"
val koin_version: String = "4.0.0"
plugins {
    kotlin("jvm") version "2.0.21"
    id("io.ktor.plugin") version "3.0.0"
    kotlin("plugin.serialization") version "2.0.21"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("org.junit.jupiter:junit-jupiter:$junit_version")
    testImplementation("org.mockito:mockito-core:5.14.2")

    // Koin for Kotlin apps
    implementation("io.insert-koin:koin-ktor:$koin_version")
    // logback
    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
//    // Koin Test features
//    testImplementation("io.insert-koin:koin-test:$koin_version")
//    // Koin for JUnit 4
//    testImplementation("io.insert-koin:koin-test-junit4:$koin_version")

}

tasks.test {
    outputs.upToDateWhen { false }
    testLogging {
        events("passed", "failed", "skipped")
    }
}

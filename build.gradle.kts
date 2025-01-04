val kotlin_version: String by project
val logback_version: String by project
val ktor_version: String by project
val junit_version: String = "5.8.1"
val koin_version: String = "4.0.0"
val hikari_version: String by project
val mariadb_version: String by project
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
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("org.junit.jupiter:junit-jupiter:$junit_version")
    testImplementation("org.mockito:mockito-core:5.14.2")

    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
    // Koin for Kotlin apps
    implementation("io.insert-koin:koin-ktor:$koin_version")
    // logback
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("net.logstash.logback:logstash-logback-encoder:7.1")

    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // db
    implementation("com.zaxxer:HikariCP:$hikari_version")
    implementation("org.mariadb.jdbc:mariadb-java-client:$mariadb_version")

}

tasks.test {
    outputs.upToDateWhen { false }
    testLogging {
        events("passed", "failed", "skipped")
    }
}

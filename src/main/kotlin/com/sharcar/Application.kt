package com.sharcar

import com.sharcar.datasource.inscription.inscriptionDatasourceModule
import com.sharcar.datasource.user.userDatasourceModule
import com.sharcar.api.*
import inscriptionRepositoryModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin
import travelModule
import userRepositoryModule

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    install(Koin) {
        modules(
            travelModule,
            inscriptionRepositoryModule,
            inscriptionDatasourceModule,
            userRepositoryModule,
            userDatasourceModule
        )
    }
    configureRouting()
}

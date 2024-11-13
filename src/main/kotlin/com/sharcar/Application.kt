package com.sharcar

import com.sharcar.datasource.inscription.inscriptionDatasourceModule
import com.sharcar.plugins.*
import inscriptionRepositoryModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import travelModule

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}

fun Application.module() {
    install(Koin) {
        modules(
            travelModule,
            inscriptionRepositoryModule,
            inscriptionDatasourceModule
        )
    }
    configureRouting()
}

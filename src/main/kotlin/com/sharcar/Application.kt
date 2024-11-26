package com.sharcar

import apiModule
import com.sharcar.api.*
import com.sharcar.exception.SharCarBadRequestException
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin
import travelModule
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

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
            apiModule,
        )
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            if (cause is SharCarBadRequestException) {
                call.respond(
                    HttpStatusCode.BadRequest, mapOf(
                        "code" to 400,
                        "error" to cause.message
                    )
                )
            } else {
                call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
            }
        }
    }
    configureRouting()
}

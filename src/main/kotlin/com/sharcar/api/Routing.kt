package com.sharcar.api

import com.sharcar.domain.usecases.user.CreateUserUsecase
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val createUserUsecase by inject<CreateUserUsecase>()


    routing {
        userRoutes(createUserUsecase)
        enterpriseRoutes()
        travelRoutes()
    }
}

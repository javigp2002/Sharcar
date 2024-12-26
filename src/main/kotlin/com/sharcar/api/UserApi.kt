package com.sharcar.api

import com.sharcar.api.dto.CreateUserDto
import com.sharcar.domain.usecases.user.CreateUserUsecase
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(createUserUsecase: CreateUserUsecase) {
    route("/user") {
        post("/register") {
            val user = call.receive<CreateUserDto>()
            call.application.environment.log.info("Creating user: ${user.email}")

            createUserUsecase.run(user.email, user.name, user.surname, user.password)

            call.respond(mapOf("email" to user.email))

        }
    }
}
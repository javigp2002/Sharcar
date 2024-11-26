package com.sharcar.api

import com.sharcar.api.dto.CreateUserDto
import com.sharcar.domain.usecases.user.CreateUserUsecase
import com.sharcar.exception.SharCarBadRequestException
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(createUserUsecase: CreateUserUsecase) {
    route("/user") {
        post("/register") {
            try {
                val user = call.receive<CreateUserDto>()
                call.application.environment.log.info("Creating user: ${user.email}")

                createUserUsecase.run(user.email, user.name, user.surname, user.password)

                call.respond(mapOf("email" to user.email))
            } catch (e: Exception) {
                call.application.environment.log.error("Error creating user", e)
                throw SharCarBadRequestException.create(601)
            }
        }
    }
}
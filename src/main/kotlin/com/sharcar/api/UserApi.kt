package com.sharcar.api

import com.sharcar.api.dto.CreateUserDto
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes() {
    route("/user") {
        post("/register") {
            val user = call.receive<CreateUserDto>()
            call.respondText("user registered: ${user.email}")
        }
    }
}
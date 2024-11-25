package com.sharcar.api

import io.ktor.server.routing.*

fun Route.userRoutes() {
    route("/user") {
        post("/register") {
        }
    }
}
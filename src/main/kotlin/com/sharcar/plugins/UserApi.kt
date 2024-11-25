package com.sharcar.plugins

import io.ktor.server.routing.*

fun Route.userRoutes() {
    route("/user") {
        post("/register") {
        }
    }
}
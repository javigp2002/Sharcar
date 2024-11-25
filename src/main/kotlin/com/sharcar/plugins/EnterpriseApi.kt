package com.sharcar.plugins

import io.ktor.server.routing.*

fun Route.enterpriseRoutes() {
    route("/enterprise") {
        post("/register") {

        }
    }
}
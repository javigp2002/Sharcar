package com.sharcar.plugins

import io.ktor.server.routing.*

fun Route.travelRoutes() {
    route("/travel") {
        post("/create") {
        }

        post("/addPassenger") {
        }

        post("/swapPassenger") {
        }
    }
}
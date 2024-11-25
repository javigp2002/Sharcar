package com.sharcar.api

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
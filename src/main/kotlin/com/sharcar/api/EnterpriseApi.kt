package com.sharcar.api

import com.sharcar.domain.usecases.enterprise.CreateEnterprise
import io.ktor.server.routing.*

fun Route.enterpriseRoutes(
    createEnterpriseUsecase: CreateEnterprise
) {
    route("/enterprise") {
        post("/register") {

        }
    }
}
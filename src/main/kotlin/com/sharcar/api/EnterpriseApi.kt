package com.sharcar.api

import com.sharcar.api.dto.RegisterEnterpriseDto
import com.sharcar.domain.usecases.enterprise.CreateEnterprise
import com.sharcar.domain.usecases.model.EnterpriseResult
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.enterpriseRoutes(
    createEnterpriseUsecase: CreateEnterprise
) {
    route("/enterprise") {
        post("/register") {
            val enterprise = call.receive<RegisterEnterpriseDto>()

            call.application.environment.log.info("Registering enterprise with nif ${enterprise.nif}")
            val createEnterpriseResult: EnterpriseResult = createEnterpriseUsecase.run(enterprise.toEnterpriseModel())

            call.application.environment.log.info("Registered ${createEnterpriseResult.success} enterprise with nif ${enterprise.nif}")
            call.respond(createEnterpriseResult)
        }
    }
}
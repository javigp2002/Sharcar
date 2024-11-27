package com.sharcar.api

import com.sharcar.api.dto.CreateTravelDto
import com.sharcar.domain.usecases.model.CreationInscriptionResult
import com.sharcar.domain.usecases.travel.CreateTravelUsecase
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.travelRoutes(createTravelUsecase: CreateTravelUsecase) {
    route("/travel") {
        post("/create") {

            val travel = call.receive<CreateTravelDto>()

            call.application.environment.log.info("Creating travel from ${travel.driverMail}")
            val createTravelResult: CreationInscriptionResult = createTravelUsecase.run(travel.toCreateTravelModel())

            call.application.environment.log.info("Created travel from ${travel.driverMail}")
            call.respond(createTravelResult)



        }

        post("/addPassenger") {
        }

        post("/swapPassenger") {
        }
    }
}
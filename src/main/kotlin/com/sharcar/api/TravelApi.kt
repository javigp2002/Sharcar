package com.sharcar.api

import com.sharcar.api.dto.AddPassengerDto
import com.sharcar.api.dto.CreateTravelDto
import com.sharcar.api.dto.SwapTravelDto
import com.sharcar.domain.usecases.model.AddPassengerResult
import com.sharcar.domain.usecases.model.CreationInscriptionResult
import com.sharcar.domain.usecases.model.SwapTravelToNewOneResult
import com.sharcar.domain.usecases.travel.AddPassengerToTravel
import com.sharcar.domain.usecases.travel.CreateTravelUsecase
import com.sharcar.domain.usecases.travel.SwapTravelToNewOne
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.travelRoutes(
    createTravelUsecase: CreateTravelUsecase,
    addPassengerUsecase: AddPassengerToTravel,
    swapTravelToNewOneUsecase: SwapTravelToNewOne
) {
    route("/travel") {
        post("/create") {
            val travel = call.receive<CreateTravelDto>()

            call.application.environment.log.info("Creating travel from ${travel.driverMail}")
            val createTravelResult: CreationInscriptionResult = createTravelUsecase.run(travel.toCreateTravelModel())

            call.application.environment.log.info("Created travel from ${travel.driverMail}")
            call.respond(createTravelResult)
        }

        post("/addPassenger") {
            val addPAssengerDto = call.receive<AddPassengerDto>()

            call.application.environment.log.info("Adding passenger ${addPAssengerDto.passengerMail} to travel ${addPAssengerDto.travelId}")
            val addPassengerResult: AddPassengerResult = addPassengerUsecase.run(addPAssengerDto.toAddPassengerModel())

            call.application.environment.log.info("Adding passenger ${addPAssengerDto.passengerMail} was ${addPassengerResult.success}")
            call.respond(addPassengerResult)
        }

        post("/swapTravel") {
            val swapTravelDto = call.receive<SwapTravelDto>()

            call.application.environment.log.info("Swapping travel ${swapTravelDto.alreadyCreatedTravelId} to ${swapTravelDto.newTravelId}")
            val swapTravelResult: SwapTravelToNewOneResult =
                swapTravelToNewOneUsecase.run(swapTravelDto.toSwapInscriptionModel())

            call.application.environment.log.info("Swapping travel ${swapTravelDto.alreadyCreatedTravelId} to ${swapTravelDto.newTravelId} was ${swapTravelResult.success}")
            call.respond(swapTravelResult)
        }
    }
}
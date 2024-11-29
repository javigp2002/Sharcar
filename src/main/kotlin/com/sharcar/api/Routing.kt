package com.sharcar.api

import com.sharcar.domain.usecases.travel.AddPassengerToTravel
import com.sharcar.domain.usecases.travel.CreateTravelUsecase
import com.sharcar.domain.usecases.travel.SwapTravelToNewOne
import com.sharcar.domain.usecases.user.CreateUserUsecase
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val createUserUsecase by inject<CreateUserUsecase>()
    val createTravelUsecase by inject<CreateTravelUsecase>()
    val addPassengerToTravel by inject<AddPassengerToTravel>()
    val swapTravelToNewOne by inject<SwapTravelToNewOne>()

    routing {
        userRoutes(createUserUsecase)
        enterpriseRoutes()
        travelRoutes(createTravelUsecase, addPassengerToTravel, swapTravelToNewOne)
    }
}

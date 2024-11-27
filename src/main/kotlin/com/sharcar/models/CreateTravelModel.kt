package com.sharcar.models

import java.time.LocalDateTime

data class CreateTravelModel(
    val enterpriseId: Int,
    val departureTime: LocalDateTime,
    val departurePlace: String,
    val arrivalPlaceId: Int,
    val driverMail: String,
    val vehicle: Int
)



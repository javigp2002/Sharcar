package com.sharcar.api.dto

import kotlinx.serialization.*

@Serializable
data class CreateTravelDto(
    val enterpriseId: Int,
    val departureTime: String,
    val departurePlace: String,
    val arrivalPlaceId: Int,
    val driverMail: String,
    val vehicle: Int
) {
    fun toCreateTravelModel() = com.sharcar.models.CreateTravelModel(
        enterpriseId = enterpriseId,
        departureTime = java.time.LocalDateTime.parse(departureTime),
        departurePlace = departurePlace,
        arrivalPlaceId = arrivalPlaceId,
        driverMail = driverMail,
        vehicle = vehicle
    )
}



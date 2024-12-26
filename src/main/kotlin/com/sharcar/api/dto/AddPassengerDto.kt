package com.sharcar.api.dto

import kotlinx.serialization.*

@Serializable
data class AddPassengerDto(
    val travelId: Int,
    val passengerMail: String
) {
    fun toAddPassengerModel() = com.sharcar.models.AddPassengerToTravelModel(
        travelId = travelId,
        passengerMail = passengerMail
    )
}



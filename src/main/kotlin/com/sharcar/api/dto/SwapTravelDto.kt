package com.sharcar.api.dto

import kotlinx.serialization.*

@Serializable
data class SwapTravelDto(
    val newTravelId: Int,
    val alreadyCreatedTravelId: Int,
    val userEmail: String
) {
    fun toSwapInscriptionModel() = com.sharcar.models.SwapInscriptionModel(
        newTravelId = newTravelId,
        alreadyCreatedTravelId = alreadyCreatedTravelId,
        userEmail = userEmail
    )
}



package com.sharcar.domain.usecases.model

import kotlinx.serialization.Serializable

@Serializable
data class AddPassengerResult(
    val success: Boolean,
)

package com.sharcar.domain.usecases.model

import kotlinx.serialization.Serializable

@Serializable
data class SwapTravelToNewOneResult(
    val success: Boolean,
    val message: String? = null
)

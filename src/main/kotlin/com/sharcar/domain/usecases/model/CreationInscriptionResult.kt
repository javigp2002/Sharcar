package com.sharcar.domain.usecases.model

import kotlinx.serialization.Serializable

@Serializable
data class CreationInscriptionResult(
    val inscriptionId: Int?,
    val success: Boolean,
    val alternative: Boolean
)

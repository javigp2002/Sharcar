package com.sharcar.domain.usecases.model

import com.sharcar.entities.Inscription

data class CreationInscriptionResult(
    val inscription: Inscription?,
    val success: Boolean,
    val alternative: Boolean
)

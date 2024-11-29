package com.sharcar.domain.usecases.model

import kotlinx.serialization.Serializable

@Serializable
data class EnterpriseResult(
    val enterpriseId: Int?,
    val success: Boolean,
)

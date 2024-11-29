package com.sharcar.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterEnterpriseDto(
    val nif: Int,
    val name: String
) {
    fun toEnterpriseModel() = com.sharcar.models.CreateEnterpriseModel(
        nif = nif,
        name = name
    )
}



package com.sharcar.api.dto

import kotlinx.serialization.*

@Serializable
data class CreateUserDto(
    val email: String,
    val name: String,
    val surname: String,
    val password: String
)

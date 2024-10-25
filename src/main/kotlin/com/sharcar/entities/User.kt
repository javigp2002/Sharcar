package com.sharcar.entities

data class User(
    val email: String,
    val name: String,
    val surname: String,
    val password: String,
    val vehicles: List<Vehicle>,
    val enterprise: Enterprise?
)

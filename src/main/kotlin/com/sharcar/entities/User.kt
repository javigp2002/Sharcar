package com.sharcar.entities

data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val password: String,
    val phoneNumber: String,
    val vehicles: List<Vehicle>,
    val enterprise: Enterprise
)

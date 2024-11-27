package com.sharcar.entities

import java.time.LocalDateTime

data class Inscription(
    val id: Int,
    val enterprise: Enterprise,
    val departureTime: LocalDateTime,
    val departurePlace: String,
    val arrivalPlace: Locations,
    val driver: User,
    val passengers: MutableList<User>,
    val vehicle: Vehicle
)

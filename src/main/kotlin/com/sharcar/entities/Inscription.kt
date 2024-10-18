package com.sharcar.entities

import java.util.*

data class Inscription(
    val id: Int,
    val departureTime: Date,
    val departurePlace: String,
    val arrivalPlace: Locations,
    val driver: User,
    val passengers: List<User>,
    val vehicle: Vehicle
)

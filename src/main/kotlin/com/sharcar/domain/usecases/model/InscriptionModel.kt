package com.sharcar.domain.usecases.model

import com.sharcar.entities.Enterprise
import com.sharcar.entities.Locations
import com.sharcar.entities.User
import com.sharcar.entities.Vehicle
import java.time.LocalDateTime

data class InscriptionModel (
    val enterprise: Enterprise,
    val departureTime: LocalDateTime,
    val departurePlace: String,
    val arrivalPlace: Locations,
    val driver: User,
    val passengers: MutableList<User>,
    val vehicle: Vehicle
)
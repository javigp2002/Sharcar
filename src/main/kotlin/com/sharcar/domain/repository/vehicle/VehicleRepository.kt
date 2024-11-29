package com.sharcar.domain.repository.vehicle

import com.sharcar.entities.Vehicle

interface VehicleRepository {
    fun findById(vehicleId: Int): Vehicle?
}
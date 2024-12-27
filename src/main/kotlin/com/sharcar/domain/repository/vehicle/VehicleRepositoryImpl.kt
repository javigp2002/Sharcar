package com.sharcar.domain.repository.vehicle

import com.sharcar.datasource.vehicle.VehicleDatasource
import com.sharcar.entities.Vehicle

class VehicleRepositoryImpl(private val datasource: VehicleDatasource) : VehicleRepository {
    override fun findById(vehicleId: Int): Vehicle? {
        return datasource.findById(vehicleId)
    }
}
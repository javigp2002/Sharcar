package com.sharcar.domain.repository.vehicle

import com.sharcar.datasource.vehicle.VehicleDatasource
import com.sharcar.entities.Vehicle

class VehicleRepositoryImpl(datasource: VehicleDatasource) : VehicleRepository {
    override fun findById(vehicleId: Int): Vehicle? {
        return null
    }
}
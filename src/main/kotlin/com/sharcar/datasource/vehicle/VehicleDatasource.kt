package com.sharcar.datasource.vehicle

import com.sharcar.datasource.DatabaseConnection
import com.sharcar.entities.Vehicle

class VehicleDatasource {
    private val database = DatabaseConnection

    fun findById(vehicleId: Int): Vehicle? {
        val result = database.executeQuery(
            "SELECT * FROM Vehicle WHERE id = ?",
            listOf(vehicleId)
        )


        if (!result.next()) {
            return null
        }
        return Vehicle(
            result.getInt("id"),
            result.getString("name"),
            result.getString("model"),
            result.getString("brand"),
            result.getInt("year"),
            result.getInt("maxPassenger")
        )
    }
}
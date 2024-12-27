package com.sharcar.datasource.location

import com.sharcar.datasource.DatabaseConnection
import com.sharcar.entities.Locations

class LocationDatasource {
    private val datasource = DatabaseConnection

    fun findById(locationId: Int): Locations? {
        val result = datasource.executeQuery(
            """
            SELECT * FROM Locations
            WHERE id = ?
        """,
            listOf<Any>(locationId)
        )
        if (!result.next())
            return null

        return Locations(
            id = result.getInt("id"),
            city = result.getString("city"),
            street = result.getString("street"),
            name = result.getString("name"),
        )
    }

}
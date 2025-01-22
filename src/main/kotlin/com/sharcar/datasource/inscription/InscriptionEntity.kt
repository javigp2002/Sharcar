package com.sharcar.datasource.inscription

import java.sql.ResultSet
import java.time.LocalDateTime

data class InscriptionEntity(
    val id: Int,
    val enterpriseId: Int,
    val departureTime: LocalDateTime,
    val departurePlace: String,
    val arrivalPlaceId: Int,
    val driverEmail: String,
    val vehicleId: Int
) {
    object Mapper {

        fun mapResultSetToInscriptionEntities(resultSet: ResultSet): List<InscriptionEntity> {
            val inscriptions = mutableListOf<InscriptionEntity>()
            while (resultSet.next()) {
                val inscription = resultToEntity(resultSet)
                inscriptions.add(inscription)
            }
            return inscriptions
        }

        fun resultToEntity(resultSet: ResultSet): InscriptionEntity {
            return InscriptionEntity(
                id = resultSet.getInt("id"),
                enterpriseId = resultSet.getInt("enterprise"),
                departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime(),
                departurePlace = resultSet.getString("departure_place"),
                arrivalPlaceId = resultSet.getInt("arrival_place"),
                driverEmail = resultSet.getString("driver"),
                vehicleId = resultSet.getInt("vehicle")
            )
        }
    }
}

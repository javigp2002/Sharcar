package com.sharcar.datasource.inscription

import com.sharcar.datasource.DatabaseConnection
import com.sharcar.domain.usecases.model.InscriptionModel
import com.sharcar.entities.Inscription
import com.sharcar.entities.User
import java.time.format.DateTimeFormatter

class InscriptionDatasource {
    private val datasource = DatabaseConnection

    fun save(inscription: InscriptionModel): Inscription {
        val result = datasource.executeInsert(
            """
            INSERT INTO Inscription 
            (enterprise, 
            departure_time, 
            departure_place, 
            arrival_place, 
            driver,
            vehicle)
            VALUES (?, ?, ?, ?, ?, ?)
            """,
            listOf<Any>(
                inscription.enterprise.id,
                inscription.departureTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                inscription.departurePlace,
                inscription.arrivalPlace.id,
                inscription.driver.email,
                inscription.vehicle.id
            )
        )



        val inscriptionResult = Inscription(
            id = result,
            enterprise = inscription.enterprise,
            departureTime = inscription.departureTime,
            departurePlace = inscription.departurePlace,
            arrivalPlace = inscription.arrivalPlace,
            driver = inscription.driver,
            passengers = mutableListOf(),
            vehicle = inscription.vehicle
        )

        return inscriptionResult
    }

    fun updatePassengerIntoInscription(inscriptionId: Int, passenger: User): Boolean {
        val result = datasource.executeNoGeneratedKey(
            """
            INSERT INTO passengers_inscription
            (inscription_id, user_id)
            VALUES (?, ?)
            """,
            listOf<Any>(inscriptionId, passenger.email)
        )

        return result == 1
    }

    fun getInscriptionsOfEnterprises(enterpriseId: Int): MutableList<InscriptionEntity> {
        val result = DatabaseConnection.executeQuery(
            """
            SELECT * FROM Inscription 
            WHERE enterprise = ?
            """,
            listOf<Any>(enterpriseId)
        )

        return InscriptionEntity.Mapper.mapResultSetToInscriptionEntities(result).toMutableList()
    }


    fun getInscriptionById(inscriptionId: Int): InscriptionEntity? {
        val result = datasource.executeQuery(
            """
            SELECT * FROM Inscription
            WHERE id = ?
            """,
            listOf<Any>(inscriptionId)
        )
        if (!result.next()) return null
        return InscriptionEntity.Mapper.resultToEntity(result)
    }

    fun getIdsNumberPassengers(inscriptionId: Int): List<String> {
        val result = datasource.executeQuery(
            """
            SELECT * FROM passengers_inscription
            WHERE inscription_id = ?
        """,
            listOf<Any>(inscriptionId)
        )

        if (!result.next()) return emptyList()

        val passengers = mutableListOf<String>()
        do {
            passengers.add(result.getString("user_id"))
        } while (result.next())

        return passengers.toList()
    }

    fun delete(inscriptionId: Int): Boolean {
        datasource.executeNoGeneratedKey(
            """
            DELETE FROM Inscription
            WHERE id = ?
            """,
            listOf<Any>(inscriptionId)
        )

        return true
    }

}
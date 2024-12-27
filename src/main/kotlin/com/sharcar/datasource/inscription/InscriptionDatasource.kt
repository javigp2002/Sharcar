package com.sharcar.datasource.inscription

import com.sharcar.datasource.DatabaseConnection
import com.sharcar.domain.usecases.model.InscriptionModel
import com.sharcar.entities.Inscription
import com.sharcar.entities.User
import java.time.format.DateTimeFormatter

class InscriptionDatasource {
    private val datasource = DatabaseConnection
    private val inscriptions = mutableListOf<Inscription>()

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
        val inscription = getInscriptionsById(inscriptionId) ?: return false
        if (inscription.vehicle.maxPassengers <= inscription.passengers.size)
            return false

        return updatePassengerIntoInscription(inscription, passenger)
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

    fun getSeatsAvailable(inscriptionId: Int): Int {
        val inscription = getInscriptionsById(inscriptionId) ?: return 0
        return inscription.vehicle.maxPassengers - inscription.passengers.size
    }

    private fun getInscriptionsById(id: Int): Inscription? {
        return inscriptions.find{ it.id == id }
    }

    private fun updatePassengerIntoInscription(inscription: Inscription, passenger: User): Boolean {
        inscription.passengers.add(passenger)
        return true
    }

    fun getInscriptionById(inscriptionId: Int): Inscription? {
        return inscriptions.find { it.id == inscriptionId }
    }

    fun delete(inscriptionId: Int): Boolean {
        val inscription = getInscriptionsById(inscriptionId) ?: return false
        inscriptions.remove(inscription)
        return true
    }

}
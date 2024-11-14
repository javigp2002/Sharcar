package com.sharcar.datasource.inscription

import com.sharcar.domain.usecases.model.InscriptionModel
import com.sharcar.entities.Inscription
import com.sharcar.entities.User

class InscriptionDatasource {
    private val inscriptions = mutableListOf<Inscription>()

    fun save(inscription: InscriptionModel): Inscription {
        val lastId = inscriptions.lastOrNull()?.id ?: 0
        val inscriptionResult = Inscription(
            lastId + 1,
            inscription.enterprise,
            inscription.departureTime,
            inscription.departurePlace,
            inscription.arrivalPlace,
            inscription.driver,
            inscription.passengers,
            inscription.vehicle
        )

        inscriptions.add(inscriptionResult)
        return inscriptionResult
    }

    fun updatePassengerIntoInscription(inscriptionId: Int, passenger: User): Boolean {
        val inscription = getInscriptionsById(inscriptionId) ?: return false
        if (inscription.vehicle.maxPassengers <= inscription.passengers.size)
            return false

        return updatePassengerIntoInscription(inscription, passenger)
    }

    fun getInscriptionsOfEnterprises(enterpriseId: Int): MutableList<Inscription> {
        return inscriptions.filter { it.enterprise.id == enterpriseId }.toMutableList()
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

}
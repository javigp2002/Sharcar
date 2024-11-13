package com.sharcar.datasource.inscription

import com.sharcar.entities.Inscription
import com.sharcar.entities.User

class InscriptionDatasource {
    private val inscriptions = mutableListOf<Inscription>()

    fun save(inscription: Inscription): Inscription {
        inscriptions.add(inscription)
        return inscription
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

}
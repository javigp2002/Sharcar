package com.sharcar.datasource

import com.sharcar.domain.InscriptionRepository
import com.sharcar.entities.Inscription
import com.sharcar.entities.User

class InscriptionDatasource: InscriptionRepository {
    private val inscriptions = mutableListOf<Inscription>()

    override fun save(inscription: Inscription): Inscription {
        inscriptions.add(inscription)
        return inscription
    }

    override fun updatePassengerIntoInscription(inscriptionId: Int, passenger: User): Boolean {
        val inscription = getInscriptionsById(inscriptionId) ?: return false
        if (inscription.vehicle.maxPassengers <= inscription.passengers.size)
            return false

        return updatePassengerIntoInscription(inscription, passenger)
    }

    override fun getInscriptionsOfEnterprises(enterpriseId: Int): Inscription? {
        return inscriptions.find { it.enterprise.id == enterpriseId }
    }

    override fun getSeatsAvailable(inscriptionId: Int): Int {
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
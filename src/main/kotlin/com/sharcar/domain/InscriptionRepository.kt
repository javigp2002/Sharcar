package com.sharcar.domain
import com.sharcar.entities.Inscription
import com.sharcar.entities.User

interface InscriptionRepository {
    fun save(inscription: Inscription): Inscription
    fun updatePassengerIntoInscription(inscriptionId: Int, passenger: User): Boolean
    fun getInscriptionsOfEnterprises(enterpriseId: Int): Inscription?
    fun getSeatsAvailable(inscriptionId: Int): Int
}
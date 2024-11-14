package com.sharcar.domain.repository.inscription

import com.sharcar.domain.usecases.model.InscriptionModel
import com.sharcar.entities.Inscription
import com.sharcar.entities.User

interface InscriptionRepository {
    fun save(inscription: InscriptionModel): Inscription
    fun delete(inscriptionId: Int): Boolean
    fun updatePassengerIntoInscription(inscriptionId: Int, passenger: User): Boolean
    fun getInscriptionsOfEnterprises(enterpriseId: Int): MutableList<Inscription>
    fun getSeatsAvailable(inscriptionId: Int): Int
    fun getInscriptionById(inscriptionId: Int): Inscription?
}
package com.sharcar.domain.repository.inscription

import com.sharcar.datasource.inscription.InscriptionDatasource
import com.sharcar.domain.usecases.model.InscriptionModel
import com.sharcar.entities.Inscription
import com.sharcar.entities.User

class InscriptionRepositoryImpl(private val datasource: InscriptionDatasource):  InscriptionRepository {
    override fun save(inscription: InscriptionModel): Inscription {
        return datasource.save(inscription)
    }

    override fun updatePassengerIntoInscription(inscriptionId: Int, passenger: User): Boolean {
        return datasource.updatePassengerIntoInscription(inscriptionId, passenger)
    }

    override fun getInscriptionsOfEnterprises(enterpriseId: Int): MutableList<Inscription> {
        return datasource.getInscriptionsOfEnterprises(enterpriseId)
    }

    override fun getSeatsAvailable(inscriptionId: Int): Int {
        return datasource.getSeatsAvailable(inscriptionId)
    }

    override fun getInscriptionById(inscriptionId: Int): Inscription? {
        return datasource.getInscriptionById(inscriptionId)
    }
}
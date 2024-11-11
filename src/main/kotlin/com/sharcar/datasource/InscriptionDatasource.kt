package com.sharcar.datasource

import com.sharcar.domain.InscriptionRepository
import com.sharcar.entities.Inscription

class InscriptionDatasource: InscriptionRepository {
    private val inscriptions = mutableListOf<Inscription>()

    override fun save(inscription: Inscription): Inscription {
        inscriptions.add(inscription)
        return inscription
    }

}
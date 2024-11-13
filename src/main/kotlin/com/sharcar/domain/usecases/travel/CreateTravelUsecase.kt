package com.sharcar.domain.usecases.travel

import com.sharcar.domain.repository.inscription.InscriptionRepository
import com.sharcar.entities.Inscription
import com.sharcar.domain.usecases.model.InscriptionModel

class CreateTravelUsecase(private val inscriptionRepository: InscriptionRepository){
    fun run(model: InscriptionModel): Inscription?{
        return null
    }

}
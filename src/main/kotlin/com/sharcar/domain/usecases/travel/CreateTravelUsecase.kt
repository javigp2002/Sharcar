package com.sharcar.domain.usecases.travel

import com.sharcar.domain.repository.inscription.InscriptionRepository
import com.sharcar.domain.usecases.model.CreationInscriptionResult
import com.sharcar.domain.usecases.model.InscriptionModel
import com.sharcar.entities.Inscription
import kotlin.math.abs

class CreateTravelUsecase(private val inscriptionRepository: InscriptionRepository){
    fun run(model: InscriptionModel): CreationInscriptionResult {
        try {
            var alternative = false
            inscriptionRepository.getInscriptionsOfEnterprises(model.enterprise.id).find {
                areCloseInscription(it, model) && userIsAbleToSeat(it)
            }?.let { alternative = true }

            return CreationInscriptionResult(inscriptionRepository.save(model), true, alternative)
        } catch (e: Exception) {
            return CreationInscriptionResult(null, false, false)
        }
    }

    private fun areCloseInscription(inscription: Inscription, model: InscriptionModel): Boolean {
        val tenMinutesMilliseconds = 10 * 60 * 1000
        return inscription.arrivalPlace == model.arrivalPlace &&
                abs(inscription.departureTime.time - model.departureTime.time) <= tenMinutesMilliseconds

    }

    private fun userIsAbleToSeat(inscription: Inscription): Boolean {
        return inscription.vehicle.maxPassengers > inscription.passengers.size
    }
}
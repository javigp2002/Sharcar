package com.sharcar.domain.usecases.travel

import com.sharcar.domain.repository.enterprise.EnterpriseRepository
import com.sharcar.domain.repository.inscription.InscriptionRepository
import com.sharcar.domain.repository.user.UserRepository
import com.sharcar.domain.repository.vehicle.VehicleRepository
import com.sharcar.domain.usecases.model.CreationInscriptionResult
import com.sharcar.domain.usecases.model.InscriptionModel
import com.sharcar.entities.Inscription
import com.sharcar.models.CreateTravelModel

class CreateTravelUsecase(
    private val inscriptionRepository: InscriptionRepository,
    private val enterpriseRepository: EnterpriseRepository,
    private val userRepository: UserRepository,
    private val vehicleRepository: VehicleRepository
) {
    fun run(request: CreateTravelModel): CreationInscriptionResult {
        var alternative = false
        val inscriptionModel = getInscriptionModel(request)

        inscriptionRepository.getInscriptionsOfEnterprises(request.enterpriseId).find {
            areCloseInscription(it, inscriptionModel) && userIsAbleToSeat(it)
        }?.let { alternative = true }

        val savedId = inscriptionRepository.save(inscriptionModel)

        return CreationInscriptionResult(savedId.id, true, alternative)

    }

    private fun areCloseInscription(inscription: Inscription, model: InscriptionModel): Boolean {
        val tenMinutesMilliseconds = 10 * 60 * 1000
        return inscription.arrivalPlace == model.arrivalPlace &&
                inscription.departureTime.compareTo(model.departureTime) <= tenMinutesMilliseconds
    }

    private fun userIsAbleToSeat(inscription: Inscription): Boolean {
        return inscription.vehicle.maxPassengers > inscription.passengers.size
    }

    private fun getInscriptionModel(request: CreateTravelModel): InscriptionModel {
        val enterprise = enterpriseRepository.findById(request.enterpriseId)
            ?: throw IllegalArgumentException("Enterprise not found")
        val arrivalPlace = enterprise.locations.find { it.id == request.arrivalPlaceId }
            ?: throw IllegalArgumentException("Arrival place not found for enterprise")
        val driver = userRepository.findByEmail(request.driverMail)
            ?: throw IllegalArgumentException("Driver not found")
        val vehicle = vehicleRepository.findById(request.vehicle)
            ?: throw IllegalArgumentException("Vehicle not found")

        return InscriptionModel(
            enterprise = enterprise,
            departureTime = request.departureTime,
            departurePlace = request.departurePlace,
            arrivalPlace = arrivalPlace,
            driver = driver,
            passengers = mutableListOf(),
            vehicle = vehicle
        )
    }
}

package com.sharcar.domain.usecases.travel

import com.sharcar.domain.repository.inscription.InscriptionRepository
import com.sharcar.domain.repository.user.UserRepository
import com.sharcar.domain.usecases.model.AddPassengerResult
import com.sharcar.models.AddPassengerToTravelModel

class AddPassengerToTravel(private val repository: InscriptionRepository, private val userRepository: UserRepository) {
    fun run(model: AddPassengerToTravelModel): AddPassengerResult {
        requireNotNull(repository.getInscriptionById(model.travelId)) {
            throw IllegalArgumentException("Travel with id ${model.travelId} does not exist")
        }

        require(repository.getSeatsAvailable(model.travelId) > 0) {
            return AddPassengerResult(false)
        }

        val user = userRepository.findByEmail(model.passengerMail)
            ?: throw IllegalArgumentException("User with email ${model.passengerMail} does not exist")

        return AddPassengerResult(repository.updatePassengerIntoInscription(model.travelId, user))
    }
}
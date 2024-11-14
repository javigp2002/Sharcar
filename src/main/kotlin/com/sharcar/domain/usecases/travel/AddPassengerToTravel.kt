package com.sharcar.domain.usecases.travel

import com.sharcar.domain.repository.inscription.InscriptionRepository
import com.sharcar.domain.repository.user.UserRepository
import com.sharcar.domain.usecases.model.AddPassengerResult

class AddPassengerToTravel(private val repository: InscriptionRepository, private val userRepository: UserRepository) {
    fun run(travelId: Int, passengerMail: String): AddPassengerResult {
        requireNotNull(repository.getInscriptionById(travelId)) {
            throw IllegalArgumentException("Travel with id $travelId does not exist")
        }

        require(repository.getSeatsAvailable(travelId) > 0) {
            return AddPassengerResult(false)
        }

        val user = userRepository.findByEmail(passengerMail)
            ?: throw IllegalArgumentException("User with email $passengerMail does not exist")

        return AddPassengerResult(repository.updatePassengerIntoInscription(travelId, user))
    }
}
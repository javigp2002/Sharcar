package com.sharcar.domain.usecases.travel

import com.sharcar.domain.repository.inscription.InscriptionRepository
import com.sharcar.domain.repository.user.UserRepository
import com.sharcar.domain.usecases.model.SwapTravelToNewOneResult
import com.sharcar.entities.Inscription

class SwapTravelToNewOne(private val repository: InscriptionRepository, private val userRepository: UserRepository) {
    fun run(newTravelId: Int, alreadyCreatedTravelId: Int, userEmail: String): SwapTravelToNewOneResult {
        val newInscription = requireNotNull(repository.getInscriptionById(newTravelId)) { "New Inscription not found" }
        val lastInscription =
            requireNotNull(repository.getInscriptionById(alreadyCreatedTravelId)) { "Last Inscription not found" }
        val user = requireNotNull(userRepository.findByEmail(userEmail)) { "User not found" }

        if (travelIsNotEmpty(newInscription))
            return SwapTravelToNewOneResult(false, "New Travel is not empty")

        if (travelIsFull(lastInscription.id))
            return SwapTravelToNewOneResult(false, "Last Travel is full")

        if (repository.delete(newInscription.id)) {
            val hasBeenUpdated = repository.updatePassengerIntoInscription(alreadyCreatedTravelId, user)
            val message: String? = if (!hasBeenUpdated) "Error updating last travel" else null
            return SwapTravelToNewOneResult(hasBeenUpdated, message)
        }

        return SwapTravelToNewOneResult(false, "Error deleting new travel")
    }

    private fun travelIsFull(inscriptionId: Int): Boolean {
        return repository.getSeatsAvailable(inscriptionId) <= 0
    }

    private fun travelIsNotEmpty(inscription: Inscription): Boolean {
        return inscription.passengers.isNotEmpty()
    }
}
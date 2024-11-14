package com.sharcar.domain.usecases.travel

import com.sharcar.domain.repository.inscription.InscriptionRepository
import com.sharcar.domain.repository.user.UserRepository
import com.sharcar.domain.usecases.model.SwapTravelToNewOneResult

class SwapTravelToNewOne(private val repository: InscriptionRepository, private val userRepository: UserRepository) {
    fun run(newTravelId: Int, alreadyCreatedTravelId: Int): SwapTravelToNewOneResult {
        return SwapTravelToNewOneResult(false);
    }
}
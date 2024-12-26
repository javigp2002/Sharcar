package com.sharcar.domain.usecases.user

import com.sharcar.domain.repository.user.UserRepository
import com.sharcar.entities.User
import com.sharcar.entities.Vehicle
import com.sharcar.exception.SharCarBadRequestException

class CreateUserUsecase (private val userRepository: UserRepository){
    fun run(email: String, name: String, surname: String, password: String): User{
        userRepository.findByEmail(email)?.let { throw SharCarBadRequestException.create(602) }

        val user = User(email, name, surname, password, mutableListOf<Vehicle>(), null)
        return userRepository.save(user)
    }
}
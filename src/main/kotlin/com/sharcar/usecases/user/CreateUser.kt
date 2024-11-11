package com.sharcar.usecases.user

import com.sharcar.domain.UserRepository
import com.sharcar.entities.User
import com.sharcar.entities.Vehicle

class CreateUserUsecase (private val userRepository: UserRepository){
    fun run(email: String, name: String, surname: String, password: String): User{
        val existingUser = userRepository.findByEmail(email)
        require(existingUser == null) { "User with email $email already exists" }

        val user = User(email, name, surname, password, mutableListOf<Vehicle>(), null)
        return userRepository.save(user)
    }
}
package com.sharcar.usecases.user

import com.sharcar.domain.UserRepository
import com.sharcar.entities.User

class CreateUserUsecase (private val userRepository: UserRepository){
    fun run(email: String, name: String, surname: String, password: String): User{
        val existingUser = userRepository.findByEmail(email)
        require(existingUser == null) { "User with email $email already exists" }

        val user = User(email, name, surname, password, emptyList(), null)
        return userRepository.save(user)
    }
}
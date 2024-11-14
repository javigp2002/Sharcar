package com.sharcar.datasource.user

import com.sharcar.entities.User

class UserDatasource {
    private val users = mutableListOf<User>()

    fun save(user: User): User {
        users.add(user)
        return user
    }

    fun findByEmail(email: String): User? {
        return users.find { it.email == email }
    }
}
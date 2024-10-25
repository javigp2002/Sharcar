package com.sharcar.datasource

import com.sharcar.domain.UserRepository
import com.sharcar.entities.User

class UserDatasource: UserRepository {
    private val users = mutableListOf<User>()

    override fun save(user: User): User {
        users.add(user)
        return user
    }

    override fun findByEmail(email: String): User? {
        return users.find { it.email == email }
    }
}
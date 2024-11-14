package com.sharcar.domain.repository.user

import com.sharcar.datasource.user.UserDatasource
import com.sharcar.entities.User

class UserRepositoryImpl(private val datasource: UserDatasource) : UserRepository {
    override fun save(user: User): User {
        return datasource.save(user)
    }

    override fun findByEmail(email: String): User? {
        return datasource.findByEmail(email)
    }
}
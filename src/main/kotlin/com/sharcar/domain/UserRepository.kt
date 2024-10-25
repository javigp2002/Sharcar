package com.sharcar.domain

import com.sharcar.entities.User

interface UserRepository {
    fun save(user: User): User
    fun findById(id: Int): User?
}
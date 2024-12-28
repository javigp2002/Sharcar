package com.sharcar.datasource.user

import com.sharcar.datasource.DatabaseConnection
import com.sharcar.entities.User

class UserDatasource {
    private val database = DatabaseConnection

    fun save(user: User): User {
        val result = database.executeQuery(
            """
            INSERT INTO User 
                (email, name, surname, password, enterprise) VALUES (?, ?, ? ,?, ?)
            """,
            listOf<Any>(user.email, user.name, user.surname, user.password, user.enterprise?.id ?: 1)
        )


        return user
    }

    fun findByEmail(email: String): User? {


        val result = database.executeQuery(
            """
            SELECT * FROM User WHERE email = ?
        """, listOf(email)
        )

        return if (result.next()) {
            User(
                email = result.getString("email"),
                name = result.getString("name"),
                surname = result.getString("surname"),
                password = result.getString("password"),
                vehicles = mutableListOf(),
                enterprise = null
            )
        } else {
            null
        }
    }
}
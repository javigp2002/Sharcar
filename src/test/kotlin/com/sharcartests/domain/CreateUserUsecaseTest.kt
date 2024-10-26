package com.sharcartests.domain

import com.sharcar.datasource.UserDatasource
import com.sharcar.entities.User
import com.sharcar.usecases.user.CreateUserUsecase
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.*

/*
HABLAR DE TESTNG
 */

class CreateUserUsecaseTest {
    private val userRepository = UserDatasource()
    private val createUserUsecase = CreateUserUsecase(userRepository)

    @BeforeTest
    fun setUp() {
        userRepository.save(User( "email", "name", "surname", "password", emptyList(), null))
    }

    @Test
    fun `Checks that user is already created`() {

        val exception = assertThrows<IllegalArgumentException> {
            createUserUsecase.run("email","email", "surname", "password")
        }

        assertEquals("User with email email already exists", exception.message)
    }

    @Test
    fun `Checks that USER is CREATED`() {
        val newMail = "Javi@gmail.com"
        val user: User = createUserUsecase.run(email = newMail,"email", "surname", "password")

        assertEquals(user.email, newMail)
    }
}
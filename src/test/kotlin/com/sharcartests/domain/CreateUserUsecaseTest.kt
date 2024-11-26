package com.sharcartests.domain

import com.sharcar.domain.repository.user.UserRepository
import com.sharcar.entities.User
import com.sharcar.entities.Vehicle
import com.sharcar.domain.usecases.user.CreateUserUsecase
import com.sharcar.exception.SharCarBadRequestException
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.*
import kotlin.test.Test
import kotlin.test.*
import org.mockito.ArgumentMatchers.any

/*
HABLAR DE TESTNG
 */

class CreateUserUsecaseTest {
   @Mock
    private val userRepository: UserRepository = mock()
    private val createUserUsecase = CreateUserUsecase(userRepository)

    @Test
    fun `Checks that user is already created`() {
        val newUser = User("email", "name", "surname", "password", mutableListOf<Vehicle>(), null)

        `when`(userRepository.findByEmail(newUser.email)).thenReturn(newUser)

        val exception = assertThrows<SharCarBadRequestException> {
            createUserUsecase.run("email","email", "surname", "password")
        }

        assertEquals("User already exists", exception.message)
    }

    @Test
    fun `Checks that USER is CREATED`() {
        val newMail = "Javi@gmail.com"
        val newUser = User(newMail, "name", "surname", "password", mutableListOf<Vehicle>(), null)

        `when`(userRepository.findByEmail(newMail)).thenReturn(null)
        `when`(userRepository.save(any(User::class.java) ?: newUser)).thenReturn(newUser)

        val user: User = createUserUsecase.run(email = newMail, name = "name", surname = "surname", password = "password")

        assertEquals(user.email, newMail)
    }
}
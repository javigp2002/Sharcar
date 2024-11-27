package com.sharcar.usecases.travel

import com.sharcar.domain.repository.inscription.InscriptionRepositoryImpl
import com.sharcar.domain.repository.user.UserRepositoryImpl
import com.sharcar.domain.usecases.travel.AddPassengerToTravel
import com.sharcar.entities.*
import org.junit.Before
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals


class AddPassengerTest {
    private val currentTime = LocalDateTime.now()
    private lateinit var vehicle1: Vehicle
    private lateinit var vehicle2: Vehicle
    private lateinit var location1: Locations
    private lateinit var enterprise1: Enterprise
    private lateinit var passenger: User
    private lateinit var inscription1: Inscription
    private lateinit var driver: User


    @Mock
    private val userRepository = mock<UserRepositoryImpl>()
    private val inscriptionRepository = mock<InscriptionRepositoryImpl>()
    private val addPassenger = AddPassengerToTravel(inscriptionRepository, userRepository)

    @Before
    fun init() {
        vehicle1 = Vehicle(1, "Clio", "Renault", "Clio", 2016, 4)
        vehicle2 = Vehicle(2, "Clio", "Renault", "Clio", 2015, 4)
        location1 = Locations(1, "Granada", "Recogidas 14", "La caleta")
        enterprise1 = Enterprise(1, "Enterprise1", mutableListOf(location1))
        passenger = User("javi@gmail.com", "driver", "Sarmiento", "pass", mutableListOf(vehicle1), enterprise1)
        driver = User("antonio@gmail.com", "driver", "Sarmiento", "pass", mutableListOf(vehicle2), enterprise1)
        inscription1 =
            Inscription(1, enterprise1, currentTime, "Gasolinera Neptuno", location1, driver, mutableListOf(), vehicle1)
    }

    @Test
    fun `Return ILLEGALARG if travelId NOT exists`() {
        `when`(inscriptionRepository.getInscriptionById(enterprise1.id)).thenReturn(null)

        val exception = assertThrows<IllegalArgumentException> {
            addPassenger.run(enterprise1.id, "javi@gmail.com")
        }

        assertEquals("Travel with id ${enterprise1.id} does not exist", exception.message)
    }

    @Test
    fun `Return false if travelId has NO seats available`() {
        `when`(inscriptionRepository.getInscriptionById(enterprise1.id)).thenReturn(inscription1)
        `when`(inscriptionRepository.getSeatsAvailable(enterprise1.id)).thenReturn(0)

        assertEquals(addPassenger.run(enterprise1.id, "javi@gmail.com").success, false)
    }

    @Test
    fun `Return ILLEGALARG if user doesnt exists`() {
        `when`(inscriptionRepository.getInscriptionById(enterprise1.id)).thenReturn(inscription1)
        `when`(inscriptionRepository.getSeatsAvailable(enterprise1.id)).thenReturn(1)
        `when`(userRepository.findByEmail(passenger.email)).thenReturn(null)

        val exception = assertThrows<IllegalArgumentException> {
            addPassenger.run(enterprise1.id, passenger.email)
        }

        assertEquals("User with email ${passenger.email} does not exist", exception.message)
    }

    @Test
    fun `Return TRUE when saved correctly`() {
        `when`(inscriptionRepository.getInscriptionById(enterprise1.id)).thenReturn(inscription1)
        `when`(inscriptionRepository.getSeatsAvailable(enterprise1.id)).thenReturn(1)
        `when`(userRepository.findByEmail(passenger.email)).thenReturn(passenger)
        `when`(inscriptionRepository.updatePassengerIntoInscription(enterprise1.id, passenger)).thenReturn(true)

        assertEquals(addPassenger.run(enterprise1.id, passenger.email).success, true)
    }

    @Test
    fun `Return FALSE when save failed`() {
        `when`(inscriptionRepository.getInscriptionById(enterprise1.id)).thenReturn(inscription1)
        `when`(inscriptionRepository.getSeatsAvailable(enterprise1.id)).thenReturn(1)
        `when`(userRepository.findByEmail(passenger.email)).thenReturn(passenger)
        `when`(inscriptionRepository.updatePassengerIntoInscription(enterprise1.id, passenger)).thenReturn(false)

        assertEquals(addPassenger.run(enterprise1.id, passenger.email).success, false)
    }

}
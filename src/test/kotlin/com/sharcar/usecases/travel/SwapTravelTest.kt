package com.sharcar.usecases.travel

import com.sharcar.domain.repository.inscription.InscriptionRepositoryImpl
import com.sharcar.domain.repository.user.UserRepositoryImpl
import com.sharcar.domain.usecases.travel.SwapTravelToNewOne
import com.sharcar.entities.*
import org.junit.Before
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals


class SwapTravelTest {
    private val currentTime = LocalDateTime.now()
    private lateinit var vehicle1: Vehicle
    private lateinit var vehicle2: Vehicle
    private lateinit var location1: Locations
    private lateinit var enterprise1: Enterprise
    private lateinit var passenger: User
    private lateinit var inscriptionEmpty: Inscription
    private lateinit var inscriptionFull: Inscription
    private lateinit var inscriptionHalf: Inscription
    private lateinit var driver: User


    @Mock
    private val userRepository = mock<UserRepositoryImpl>()
    private val inscriptionRepository = mock<InscriptionRepositoryImpl>()
    private val swapTravel = SwapTravelToNewOne(inscriptionRepository, userRepository)

    @Before
    fun init() {
        vehicle1 = Vehicle(1, "Clio", "Renault", "Clio", 2016, 4)
        vehicle2 = Vehicle(2, "Clio", "Renault", "Clio", 2015, 4)
        location1 = Locations(1, "Granada", "Recogidas 14", "La caleta")
        enterprise1 = Enterprise(1, "Enterprise1", mutableListOf(location1))
        passenger = User("javi@gmail.com", "passenger", "Sarmiento", "pass", mutableListOf(vehicle1), enterprise1)
        driver = User("antonio@gmail.com", "driver", "Sarmiento", "pass", mutableListOf(vehicle2), enterprise1)
        inscriptionEmpty =
            Inscription(1, enterprise1, currentTime, "Gasolinera Neptuno", location1, driver, mutableListOf(), vehicle1)
        inscriptionFull =
            Inscription(
                2,
                enterprise1,
                LocalDateTime.now(),
                "Gasolinera Neptuno",
                location1,
                driver,
                mutableListOf(passenger, passenger, passenger, passenger),
                vehicle1
            )
        inscriptionHalf =
            Inscription(
                3,
                enterprise1,
                LocalDateTime.now(),
                "Gasolinera Neptuno",
                location1,
                driver,
                mutableListOf(passenger, passenger),
                vehicle1
            )
    }

    @Test
    fun `Return ILLEGALARG if newInscription doesnt exists`() {
        `when`(inscriptionRepository.getInscriptionById(inscriptionEmpty.id)).thenReturn(null)

        val exception = assertThrows<IllegalArgumentException> {
            swapTravel.run(inscriptionEmpty.id, inscriptionFull.id, passenger.email)
        }

        assertEquals("New Inscription not found", exception.message)
    }

    @Test
    fun `Return ILLEGALARG if LastInscription doesnt exists`() {
        `when`(inscriptionRepository.getInscriptionById(inscriptionEmpty.id)).thenReturn(inscriptionEmpty)
        `when`(inscriptionRepository.getInscriptionById(inscriptionFull.id)).thenReturn(null)

        val exception = assertThrows<IllegalArgumentException> {
            swapTravel.run(inscriptionEmpty.id, inscriptionFull.id, passenger.email)
        }

        assertEquals("Last Inscription not found", exception.message)
    }

    @Test
    fun `Return ILLEGALARG if user doesnt exists`() {
        `when`(inscriptionRepository.getInscriptionById(inscriptionEmpty.id)).thenReturn(inscriptionEmpty)
        `when`(inscriptionRepository.getInscriptionById(inscriptionFull.id)).thenReturn(inscriptionFull)
        `when`(userRepository.findByEmail(passenger.email)).thenReturn(null)


        val exception = assertThrows<IllegalArgumentException> {
            swapTravel.run(inscriptionEmpty.id, inscriptionFull.id, passenger.email)
        }

        assertEquals("User not found", exception.message)
    }

    @Test
    fun `Return Negative Result as Inscription to delete is not empty of passengers`() {
        `when`(inscriptionRepository.getInscriptionById(inscriptionHalf.id)).thenReturn(inscriptionHalf)
        `when`(inscriptionRepository.getInscriptionById(inscriptionFull.id)).thenReturn(inscriptionFull)
        `when`(userRepository.findByEmail(passenger.email)).thenReturn(passenger)


        val result = swapTravel.run(inscriptionHalf.id, inscriptionFull.id, passenger.email)
        assertEquals(result.success, false)
    }

    @Test
    fun `Return Negative Result as Inscription to insert passenger is Full of passengers`() {
        `when`(inscriptionRepository.getInscriptionById(inscriptionEmpty.id)).thenReturn(inscriptionEmpty)
        `when`(inscriptionRepository.getInscriptionById(inscriptionFull.id)).thenReturn(inscriptionFull)
        `when`(userRepository.findByEmail(passenger.email)).thenReturn(passenger)
        `when`(inscriptionRepository.getSeatsAvailable(inscriptionFull.id)).thenReturn(0)

        val result = swapTravel.run(inscriptionEmpty.id, inscriptionFull.id, passenger.email)
        assertEquals(result.success, false)
    }

    @Test
    fun `Return Negative Result if result of delete is false`() {
        `when`(inscriptionRepository.getInscriptionById(inscriptionEmpty.id)).thenReturn(inscriptionEmpty)
        `when`(inscriptionRepository.getInscriptionById(inscriptionHalf.id)).thenReturn(inscriptionHalf)
        `when`(userRepository.findByEmail(passenger.email)).thenReturn(passenger)
        `when`(inscriptionRepository.getSeatsAvailable(inscriptionHalf.id)).thenReturn(2)
        `when`(inscriptionRepository.delete(inscriptionEmpty.id)).thenReturn(false)

        val result = swapTravel.run(inscriptionEmpty.id, inscriptionHalf.id, passenger.email)
        assert(!result.success && result.message == "Error deleting new travel")
    }

    @Test
    fun `Return Negative Result if updating has been wrong`() {
        `when`(inscriptionRepository.getInscriptionById(inscriptionEmpty.id)).thenReturn(inscriptionEmpty)
        `when`(inscriptionRepository.getInscriptionById(inscriptionHalf.id)).thenReturn(inscriptionHalf)
        `when`(userRepository.findByEmail(passenger.email)).thenReturn(passenger)
        `when`(inscriptionRepository.getSeatsAvailable(inscriptionHalf.id)).thenReturn(2)
        `when`(inscriptionRepository.delete(inscriptionEmpty.id)).thenReturn(true)
        `when`(inscriptionRepository.updatePassengerIntoInscription(inscriptionHalf.id, passenger)).thenReturn(false)

        val result = swapTravel.run(inscriptionEmpty.id, inscriptionHalf.id, passenger.email)
        assert(!result.success && result.message == "Error updating last travel")
    }

    @Test
    fun `Return Success if everything is ok`() {
        `when`(inscriptionRepository.getInscriptionById(inscriptionEmpty.id)).thenReturn(inscriptionEmpty)
        `when`(inscriptionRepository.getInscriptionById(inscriptionHalf.id)).thenReturn(inscriptionHalf)
        `when`(userRepository.findByEmail(passenger.email)).thenReturn(passenger)
        `when`(inscriptionRepository.getSeatsAvailable(inscriptionHalf.id)).thenReturn(2)
        `when`(inscriptionRepository.delete(inscriptionEmpty.id)).thenReturn(true)
        `when`(inscriptionRepository.updatePassengerIntoInscription(inscriptionHalf.id, passenger)).thenReturn(true)

        val result = swapTravel.run(inscriptionEmpty.id, inscriptionHalf.id, passenger.email)
        assert(result.success && result.message == null)
    }

}
package com.sharcar.usecases.travel

import com.sharcar.domain.repository.enterprise.EnterpriseRepositoryImpl
import com.sharcar.domain.repository.inscription.InscriptionRepositoryImpl
import com.sharcar.domain.repository.user.UserRepository
import com.sharcar.domain.repository.vehicle.VehicleRepository
import com.sharcar.domain.usecases.model.InscriptionModel
import com.sharcar.domain.usecases.travel.CreateTravelUsecase
import com.sharcar.entities.*
import com.sharcar.models.CreateTravelModel
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.*
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class CreateTravelTest {
    private val currentTime = LocalDateTime.now()
    private lateinit var vehicle1: Vehicle
    private lateinit var vehicle2: Vehicle
    private lateinit var location1: Locations
    private lateinit var enterprise1: Enterprise
    private lateinit var driver: User
    private lateinit var inscription1: Inscription
    private lateinit var model: InscriptionModel
    private lateinit var travelModel: CreateTravelModel

    @Mock
    private val inscriptionRepository = mock<InscriptionRepositoryImpl>()
    private val enterpriseRepository = mock<EnterpriseRepositoryImpl>()
    private val userRepository = mock<UserRepository>()
    private val vehicleRepository = mock<VehicleRepository>()

    private val createTravel =
        CreateTravelUsecase(inscriptionRepository, enterpriseRepository, userRepository, vehicleRepository)

    @Before
    fun init() {
        vehicle1 = Vehicle(1, "Clio", "Renault", "Clio", 2016, 4)
        vehicle2 = Vehicle(2, "Clio", "Renault", "Clio", 2015, 4)
        location1 = Locations(1, "Granada", "Recogidas 14", "La caleta")
        enterprise1 = Enterprise(1, "Enterprise1", mutableListOf(location1))
        driver = User("javi@gmail.com", "driver", "Sarmiento", "pass", mutableListOf(vehicle1), enterprise1)
        inscription1 =
            Inscription(
                1,
                enterprise1,
                currentTime,
                "Gasolinera Neptuno",
                location1,
                driver,
                mutableListOf(),
                vehicle1
            )
        model = InscriptionModel(
            enterprise1,
            currentTime,
            "Gasolinera Neptuno",
            location1,
            driver,
            mutableListOf(),
            vehicle1
        )

        travelModel = CreateTravelModel(
            1,
            currentTime,
            "Gasolinera Neptuno",
            1,
            "javi@gmail.com",
            1,
        )
        `when`(enterpriseRepository.findById(travelModel.enterpriseId)).thenReturn(
            enterprise1
        )
        `when`(userRepository.findByEmail(travelModel.driverMail)).thenReturn(
            driver
        )
        `when`(vehicleRepository.findById(travelModel.vehicle)).thenReturn(
            vehicle1
        )
    }



    @Test
    fun `Return alternative FALSE if not seats available on similar inscription`() {
        val newUser = User("antonio@gmail.com", "driver", "Sarmiento", "pass", mutableListOf(vehicle2), enterprise1)

        val inscriptionAlreadyExists =
            Inscription(
                2,
                enterprise1,
                currentTime.plusMinutes(5),
                "Gasolinera Neptuno",
                location1,
                newUser,
                mutableListOf(
                    driver, driver, driver, driver
                ),
                vehicle2
            )

        `when`(inscriptionRepository.getInscriptionsOfEnterprises(enterprise1.id)).thenReturn(
            mutableListOf(inscriptionAlreadyExists)
        )
        `when`(inscriptionRepository.save(model)).thenReturn(
            inscription1
        )

        val creationInscriptionResult = createTravel.run(travelModel)
        assertTrue(creationInscriptionResult.success && !creationInscriptionResult.alternative)
    }

    @Test
    fun `Return alternative FALSE if similar inscription`() {
        `when`(inscriptionRepository.getInscriptionsOfEnterprises(enterprise1.id)).thenReturn(
            mutableListOf()
        )
        `when`(inscriptionRepository.save(model)).thenReturn(
            inscription1
        )

        val creationInscriptionResult = createTravel.run(travelModel)
        assertTrue(creationInscriptionResult.success && !creationInscriptionResult.alternative)
    }

    @Test
    fun `Return alternative TRUE if similar inscription and able to seat`() {
        val newUser = User("antonio@gmail.com", "driver", "Sarmiento", "pass", mutableListOf(vehicle2), enterprise1)

        val inscriptionAlreadyExists =
            Inscription(
                2,
                enterprise1,
                currentTime.plusMinutes(5),
                "Gasolinera Neptuno",
                location1,
                newUser,
                mutableListOf(),
                vehicle2
            )

        `when`(inscriptionRepository.getInscriptionsOfEnterprises(enterprise1.id)).thenReturn(
            mutableListOf(inscriptionAlreadyExists)
        )
        `when`(inscriptionRepository.save(model)).thenReturn(
            inscription1
        )

        val creationInscriptionResult = createTravel.run(travelModel)
        assertTrue(creationInscriptionResult.success && creationInscriptionResult.alternative)
    }

    @Test
    fun `Return new inscription created`() {
        val newUser = User("antonio@gmail.com", "driver", "Sarmiento", "pass", mutableListOf(vehicle2), enterprise1)

        val inscriptionAlreadyExists =
            Inscription(
                2,
                enterprise1,
                currentTime.plusMinutes(5),
                "Gasolinera Neptuno",
                location1,
                newUser,
                mutableListOf(),
                vehicle2
            )

        `when`(inscriptionRepository.getInscriptionsOfEnterprises(enterprise1.id)).thenReturn(
            mutableListOf(inscriptionAlreadyExists)
        )
        `when`(inscriptionRepository.save(model)).thenReturn(
            inscription1
        )

        val creationInscriptionResult = createTravel.run(travelModel)
        assertEquals(inscription1.id, creationInscriptionResult.inscriptionId)
    }

}
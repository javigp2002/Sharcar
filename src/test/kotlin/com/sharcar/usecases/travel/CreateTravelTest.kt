package com.sharcar.usecases.travel

import com.sharcar.domain.repository.inscription.InscriptionRepositoryImpl
import com.sharcar.domain.usecases.model.InscriptionModel
import com.sharcar.domain.usecases.travel.CreateTravelUsecase
import com.sharcar.entities.*
import org.junit.Before
import org.mockito.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.*
import kotlin.test.Test
import kotlin.test.*


class CreateTravelTest {
    private lateinit var vehicle1: Vehicle
    private lateinit var location1: Locations
    private lateinit var enterprise1: Enterprise
    private lateinit var driver: User
    private lateinit var inscription1: Inscription
    private lateinit var model: InscriptionModel

    @Mock
    private val inscriptionRepository = mock<InscriptionRepositoryImpl>()
    private val createTravel = CreateTravelUsecase(inscriptionRepository)

    @Before
    fun init() {
        val currentTime = Date()
        vehicle1 = Vehicle(1, "Clio", "Renault", "Clio", 2016, 4)
        location1 = Locations(1, "Granada", "Recogidas 14", "La caleta")
        enterprise1 = Enterprise(1, "Enterprise1", mutableListOf(location1))
        driver = User("javi@gmail.com", "driver", "Sarmiento", "pass", mutableListOf(vehicle1), enterprise1)
        inscription1 =
            Inscription(1, enterprise1, currentTime, "Gasolinera Neptuno", location1, driver, mutableListOf(), vehicle1)
        model = InscriptionModel(
            enterprise1,
            currentTime,
            "Gasolinera Neptuno",
            location1,
            driver,
            mutableListOf(),
            vehicle1
        )

    }

    @Test
    fun `Return bad result if repository return exception`() {
        `when`(inscriptionRepository.getInscriptionsOfEnterprises(enterprise1.id)).thenThrow(
            RuntimeException("Error")
        )

        val creationInscriptionResult = createTravel.run(model)
        assertTrue(!creationInscriptionResult.success)
    }


}
package com.unit.api.travelroutes

import com.sharcar.api.dto.AddPassengerDto
import com.sharcar.api.dto.CreateTravelDto
import com.sharcar.api.dto.SwapTravelDto
import com.sharcar.api.travelRoutes
import com.sharcar.domain.usecases.model.AddPassengerResult
import com.sharcar.domain.usecases.model.CreationInscriptionResult
import com.sharcar.domain.usecases.model.SwapTravelToNewOneResult
import com.sharcar.domain.usecases.travel.AddPassengerToTravel
import com.sharcar.domain.usecases.travel.CreateTravelUsecase
import com.sharcar.domain.usecases.travel.SwapTravelToNewOne
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.Test
import kotlin.test.assertEquals


class TravelRoutesTest {
    @Mock
    private val mockCreateTravelUsecase = mock<CreateTravelUsecase>()
    private val mockAddPassengerToTravel = mock<AddPassengerToTravel>()
    private val mockSwapTravelToNewOne = mock<SwapTravelToNewOne>()

    fun TestApplicationBuilder.configureTestApplication() {
        application {
            install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) {
                json()
            }
            routing {
                travelRoutes(
                    createTravelUsecase = mockCreateTravelUsecase,
                    addPassengerUsecase = mockAddPassengerToTravel,
                    swapTravelToNewOneUsecase = mockSwapTravelToNewOne
                )
            }
        }
    }

    @Test
    fun `CREATE TRAVEL-Creates travel and RETURNS with alternative`() = testApplication {
        configureTestApplication()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val dto = CreateTravelDto(
            1, "2007-12-03T10:15:30", "Gasolinera", 1,
            "usuario@gmail.com", 1
        )

        `when`(mockCreateTravelUsecase.run(dto.toCreateTravelModel())).thenReturn(
            CreationInscriptionResult(1, true, true)
        )

        val response = client.post("/travel/create") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val result = response.body<CreationInscriptionResult>()
        assertEquals(CreationInscriptionResult(1, true, true), result)
    }

    @Test
    fun `CREATE TRAVEL-Everything ok but Travel Already created`() = testApplication {
        configureTestApplication()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val dto = CreateTravelDto(
            1, "2007-12-03T10:15:30", "Gasolinera", 1,
            "usuario@gmail.com", 1
        )

        `when`(mockCreateTravelUsecase.run(dto.toCreateTravelModel())).thenReturn(
            CreationInscriptionResult(0, false, false)
        )

        val response = client.post("/travel/create") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val result = response.body<CreationInscriptionResult>()
        assertEquals(CreationInscriptionResult(0, false, false), result)
    }

    @Test
    fun `CREATE TRAVEL-DTO didnt match with operation`() = testApplication {
        configureTestApplication()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/travel/create") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                    "id": 1,
                    "date": "2007-12-03T10:15:30",
                    "place": "Gasolinera",
                    "driverId": 1,
                }
            """.trimIndent()
            )
        }
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `ADD PASSENGER-Adds passenger and returns success`() = testApplication {
        configureTestApplication()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val dto = AddPassengerDto(1, "prueba@gmail.com")

        `when`(mockAddPassengerToTravel.run(dto.toAddPassengerModel())).thenReturn(
            AddPassengerResult(true)
        )

        val response = client.post("/travel/addPassenger") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val result = response.body<AddPassengerResult>()
        assertEquals(AddPassengerResult(true), result)
    }

    @Test
    fun `ADD PASSENGER-Everything ok but Travel Already created`() = testApplication {
        configureTestApplication()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val dto = AddPassengerDto(1, "prueba@gmail.com")

        `when`(mockAddPassengerToTravel.run(dto.toAddPassengerModel())).thenReturn(
            AddPassengerResult(false)
        )

        val response = client.post("/travel/addPassenger") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val result = response.body<AddPassengerResult>()
        assertEquals(AddPassengerResult(false), result)
    }

    @Test
    fun `ADD PASSENGER-DTO didnt match with operation`() = testApplication {
        configureTestApplication()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/travel/create") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                    "id": 1
                }
            """.trimIndent()
            )
        }
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `SWAP TRAVEL-Returns false if something is not ok`() = testApplication {
        configureTestApplication()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val dto = SwapTravelDto(1, 2, "usuario@gmail.com")

        `when`(mockSwapTravelToNewOne.run(dto.toSwapInscriptionModel())).thenReturn(
            SwapTravelToNewOneResult(false)
        )

        val response = client.post("/travel/swapTravel") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val result = response.body<SwapTravelToNewOneResult>()
        assertEquals(SwapTravelToNewOneResult(false), result)
    }

    @Test
    fun `SWAP TRAVEL-Returns true if transaction is ok`() = testApplication {
        configureTestApplication()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val dto = SwapTravelDto(1, 2, "usuario@gmail.com")

        `when`(mockSwapTravelToNewOne.run(dto.toSwapInscriptionModel())).thenReturn(
            SwapTravelToNewOneResult(true)
        )

        val response = client.post("/travel/swapTravel") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val result = response.body<SwapTravelToNewOneResult>()
        assertEquals(SwapTravelToNewOneResult(true), result)
    }

    @Test
    fun `SWAP TRAVEL-DTO didnt match with operation`() = testApplication {
        configureTestApplication()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/travel/create") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                    "id": 1
                }
            """.trimIndent()
            )
        }
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }


}
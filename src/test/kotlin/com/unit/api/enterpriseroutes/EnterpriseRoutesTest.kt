package com.unit.api.enterpriseroutes

import com.sharcar.api.dto.RegisterEnterpriseDto
import com.sharcar.api.enterpriseRoutes
import com.sharcar.domain.usecases.enterprise.CreateEnterprise
import com.sharcar.domain.usecases.model.EnterpriseResult
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


class EnterpriseRoutesTest {
    @Mock
    private val mockCreateEnterprise = mock<CreateEnterprise>()

    fun TestApplicationBuilder.configureTestApplication() {
        application {
            install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) {
                json()
            }
            routing {
                enterpriseRoutes(
                    mockCreateEnterprise
                )
            }
        }
    }

    @Test
    fun `REGISTER ENTERPRISE-Register ok enterprise`() = testApplication {
        configureTestApplication()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val dto = RegisterEnterpriseDto(123456789, "name")

        `when`(mockCreateEnterprise.run(dto.toEnterpriseModel())).thenReturn(
            EnterpriseResult(1, true)
        )

        val response = client.post("/enterprise/register") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val result = response.body<EnterpriseResult>()
        assertEquals(EnterpriseResult(1, true), result)
    }

    @Test
    fun `REGISTER ENTERPRISE-DTO not satisfied`() = testApplication {
        configureTestApplication()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/enterprise/register") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                    "nif": 123456789,
                }
            """.trimIndent()
            )
        }
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }


}
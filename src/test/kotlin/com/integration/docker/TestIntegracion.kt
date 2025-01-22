package com.integration.docker

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class TestIntegracion {

    @Test
    fun `DOCKER-Returns docker`() = runBlocking {
        val client = HttpClient(CIO) {

        }

        val response: HttpResponse = client.request("http://localhost:8080/docker/test_database") {
            method = HttpMethod.Get
        }

        println(response)

        assertEquals("Database test success", response.body())
    }
}
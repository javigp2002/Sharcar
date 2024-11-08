package com.sharcartests.domain

import com.sharcar.datasource.EnterpriseDatasource
import com.sharcar.entities.Enterprise
import com.sharcar.usecases.enterprise.CreateEnterprise
import org.junit.jupiter.api.assertThrows
import org.mockito.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.Test
import kotlin.test.*


class CreateEnterpriseTest{
    @Mock
    private var enterpriseDatasourceMock: EnterpriseDatasource = mock();
    val createEnterprise = CreateEnterprise(enterpriseDatasourceMock)

     @Test
     fun `Checks that enterprise is already created`() {
         `when`(enterpriseDatasourceMock.findById(123456789)).thenReturn(null)

         val exception = assertThrows<IllegalArgumentException> {
             createEnterprise.run(123456789, "name")
         }
         assertEquals("Enterprise with nif 123456789 already exists", exception.message)
     }

     @Test
     fun `Checks that ENTERPRISE is CREATED`() {
         `when`(enterpriseDatasourceMock.findById(987654321)).thenAnswer { Enterprise(987654321, "name", emptyList()) }
         `when`(enterpriseDatasourceMock.save(Enterprise(987654321, "name", emptyList()))).thenReturn(true)

         val newNif = 987654321
         val enterprise: Enterprise = createEnterprise.run(newNif, "name")
         assertEquals(enterprise.id, newNif)
     }
}
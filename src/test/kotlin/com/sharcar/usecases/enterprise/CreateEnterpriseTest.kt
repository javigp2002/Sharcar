package com.sharcartests.domain

import com.sharcar.domain.repository.enterprise.EnterpriseRepositoryImpl
import com.sharcar.domain.usecases.enterprise.CreateEnterprise
import com.sharcar.entities.Enterprise
import com.sharcar.models.CreateEnterpriseModel
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.Test
import kotlin.test.assertEquals


class CreateEnterpriseTest{
    @Mock
    private var enterpriseDatasourceMock = mock<EnterpriseRepositoryImpl>()
    private val createEnterprise = CreateEnterprise(enterpriseDatasourceMock)

     @Test
     fun `Checks that enterprise is already created`() {
         `when`(enterpriseDatasourceMock.findById(123456789)).thenReturn(null)
         val model = CreateEnterpriseModel(123456789, "name")

         val exception = assertThrows<IllegalArgumentException> {
             createEnterprise.run(model)
         }
         assertEquals("Enterprise with nif 123456789 already exists", exception.message)
     }

     @Test
     fun `Checks that ENTERPRISE is CREATED`() {
         val nif = 987654321
         `when`(enterpriseDatasourceMock.findById(nif)).thenAnswer { Enterprise(nif, "name", emptyList()) }
         `when`(enterpriseDatasourceMock.save(Enterprise(nif, "name", emptyList()))).thenReturn(true)

         val model = CreateEnterpriseModel(nif, "name")

         val enterprise: Enterprise = createEnterprise.run(model)
         assertEquals(enterprise.id, nif)
     }
}
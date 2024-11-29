package com.sharcar.domain.usecases.enterprise

import com.sharcar.domain.repository.enterprise.EnterpriseRepository
import com.sharcar.entities.Enterprise
import com.sharcar.models.CreateEnterpriseModel

class CreateEnterprise (private val enterpriseRepository: EnterpriseRepository){
    fun run(enterpriseModel: CreateEnterpriseModel): Enterprise {
        val isEnterpriseCreated = enterpriseRepository.findById(enterpriseModel.nif)
        require(isEnterpriseCreated != null) { "Enterprise with nif ${enterpriseModel.nif} already exists" }

        val enterprise = Enterprise(enterpriseModel.nif, enterpriseModel.name, emptyList())
        val wasCreated =  enterpriseRepository.save(enterprise)
        require(wasCreated) { "Enterprise could not be created" }
        return enterprise
    }
}
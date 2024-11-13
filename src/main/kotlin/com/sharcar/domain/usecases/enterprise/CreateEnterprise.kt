package com.sharcar.domain.usecases.enterprise

import com.sharcar.domain.EnterpriseRepository
import com.sharcar.entities.Enterprise

class CreateEnterprise (private val enterpriseRepository: EnterpriseRepository){
    fun run(nif: Int, name: String): Enterprise{
        val isEnterpriseCreated = enterpriseRepository.findById(nif)
        require(isEnterpriseCreated != null) { "Enterprise with nif $nif already exists"}

        val enterprise = Enterprise(nif, name, emptyList())
        val wasCreated =  enterpriseRepository.save(enterprise)
        require(wasCreated) { "Enterprise could not be created" }
        return enterprise
    }
}
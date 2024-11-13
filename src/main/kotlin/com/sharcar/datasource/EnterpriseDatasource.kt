package com.sharcar.datasource

import com.sharcar.domain.repository.enterprise.EnterpriseRepository
import com.sharcar.entities.Enterprise

class EnterpriseDatasource: EnterpriseRepository {
    private val enterprises = mutableListOf<Enterprise>()

    override fun save(enterprise: Enterprise): Boolean {
       enterprises.add(enterprise)
        return true
    }

    override fun findById(id: Int): Enterprise? {
       return enterprises.find { it.id == id }
    }
}
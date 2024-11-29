package com.sharcar.domain.repository.enterprise

import EnterpriseDatasource
import com.sharcar.entities.Enterprise

class EnterpriseRepositoryImpl(datasource: EnterpriseDatasource) : EnterpriseRepository {
    override fun save(enterprise: Enterprise): Boolean {
        return true
    }

    override fun findById(id: Int): Enterprise? {
        return null
    }
}
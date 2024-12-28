package com.sharcar.domain.repository.enterprise

import EnterpriseDatasource
import com.sharcar.entities.Enterprise

class EnterpriseRepositoryImpl(private val datasource: EnterpriseDatasource) : EnterpriseRepository {
    override fun save(enterprise: Enterprise): Boolean {
        return datasource.save(enterprise)
    }

    override fun findById(id: Int): Enterprise? {
        return datasource.findById(id)
    }
}
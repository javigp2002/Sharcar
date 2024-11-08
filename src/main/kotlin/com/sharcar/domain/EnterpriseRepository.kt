package com.sharcar.domain

import com.sharcar.entities.Enterprise

interface EnterpriseRepository {
    fun save (enterprise: Enterprise): Boolean
    fun findById (id: Int): Enterprise?
}
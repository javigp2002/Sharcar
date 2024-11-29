package com.sharcar.datasource.enterprise

import EnterpriseDatasource
import org.koin.dsl.module

val enterpriseDatasourceModule = module {
    single { EnterpriseDatasource() }
}
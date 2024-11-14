package com.sharcar.datasource.user

import org.koin.dsl.module

val userDatasourceModule = module {
    single { UserDatasource() }
}
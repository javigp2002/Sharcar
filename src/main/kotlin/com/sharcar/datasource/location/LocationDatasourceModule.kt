package com.sharcar.datasource.location

import org.koin.dsl.module

val locationDatasourceModule = module {
    single { LocationDatasource() }
}
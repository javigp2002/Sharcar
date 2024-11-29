package com.sharcar.datasource.vehicle

import org.koin.dsl.module

val vehicleDatasourceModule = module {
    single { VehicleDatasource() }
}
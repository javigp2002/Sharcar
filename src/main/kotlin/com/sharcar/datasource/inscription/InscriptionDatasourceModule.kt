package com.sharcar.datasource.inscription
import org.koin.dsl.module

val inscriptionDatasourceModule = module {
    single { InscriptionDatasource() }
}
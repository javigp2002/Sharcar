package com.sharcar.domain.usecases.user

import org.koin.dsl.module
import userRepositoryModule

val userUsecaseModule = module {
    includes(userRepositoryModule)

    single { CreateUserUsecase(get()) }
}
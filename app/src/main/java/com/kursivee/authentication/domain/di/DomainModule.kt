package com.kursivee.authentication.domain.di

import com.kursivee.authentication.domain.LoginCache
import com.kursivee.authentication.domain.LoginRepository
import com.kursivee.authentication.domain.LoginUseCase
import com.kursivee.authentication.domain.UserDaoUseCase
import org.koin.dsl.module

object DomainModule {
    val modules = module {
        single { LoginRepository(get(), get()) }
        single { LoginUseCase(get(), get()) }
        single { LoginCache(get()) }
        single { UserDaoUseCase(get()) }
    }
}
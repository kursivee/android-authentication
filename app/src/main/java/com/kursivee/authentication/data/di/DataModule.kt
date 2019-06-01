package com.kursivee.authentication.data.di

import com.kursivee.authentication.data.LoginCache
import com.kursivee.authentication.data.LoginService
import org.koin.dsl.module

object DataModule {
    val modules = module {
        single { LoginService() }
        single { LoginCache() }
    }
}
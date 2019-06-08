package com.kursivee.authentication.view.util.di

import com.kursivee.authentication.view.util.AlertFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object UtilModule {
    val module = module {
        single { AlertFactory(androidContext()) }
    }
}

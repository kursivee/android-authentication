package com.kursivee.authentication.view.main.di

import com.kursivee.authentication.view.main.MainProgressBarComponent
import org.koin.dsl.module

object MainModule {
    val module = module {
        factory {
            MainProgressBarComponent(getProperty(MainProgressBarComponent.PB_CONTAINER_ID), get())
        }
    }
}
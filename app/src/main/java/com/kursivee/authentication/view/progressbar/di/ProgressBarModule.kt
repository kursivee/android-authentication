package com.kursivee.authentication.view.progressbar.di

import com.kursivee.authentication.view.progressbar.ProgressBarJob
import org.koin.dsl.module

object ProgressBarModule {
    val modules = module {
        factory { ProgressBarJob() }
    }
}
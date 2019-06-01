package com.kursivee.authentication.view.login.di

import com.kursivee.authentication.view.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object LoginViewModule {
    val modules = module {
        viewModel { LoginViewModel(get(), get()) }
    }
}
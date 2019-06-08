package com.kursivee.authentication.view.authentication.di

import com.kursivee.authentication.R
import com.kursivee.authentication.view.authentication.AuthenticationDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AuthenticationModule {
    val module = module {
        single {
            AuthenticationDelegate(
                androidContext().resources.getString(R.string.default_web_client_id)
            )
        }
    }
}
package com.kursivee.authentication

import android.app.Application
import com.kursivee.authentication.data.di.DataModule
import com.kursivee.authentication.domain.di.DomainModule
import com.kursivee.authentication.view.login.di.LoginViewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(
                DataModule.modules,
                DomainModule.modules,
                LoginViewModule.modules
            ))
        }
    }
}
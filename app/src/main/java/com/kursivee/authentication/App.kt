package com.kursivee.authentication

import android.app.Application
import androidx.room.Room
import com.kursivee.authentication.data.db.AppDatabase
import com.kursivee.authentication.data.di.DataModule
import com.kursivee.authentication.domain.di.DomainModule
import com.kursivee.authentication.view.login.di.LoginViewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        /*
            Adding global context check to handle issue with robolectric tests
            Koin starts multiple times due to robolectric tests running in parallel
            https://github.com/InsertKoinIO/koin/issues/254
         */
        if(GlobalContext.getOrNull() == null) {
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
}

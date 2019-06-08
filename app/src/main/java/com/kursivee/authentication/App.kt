package com.kursivee.authentication

import android.app.Application
import com.kursivee.authentication.data.di.DataModule
import com.kursivee.authentication.domain.di.DomainModule
import com.kursivee.authentication.view.authentication.di.AuthenticationModule
import com.kursivee.authentication.view.login.di.LoginViewModule
import com.kursivee.authentication.view.progressbar.di.ProgressBarModule
import com.kursivee.authentication.view.util.di.UtilModule
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
                    UtilModule.module,
                    AuthenticationModule.module,
                    DataModule.module,
                    DomainModule.module,
                    LoginViewModule.module,
                    ProgressBarModule.module
                ))
            }
        }
    }
}

package com.kursivee.authentication.data.di

import androidx.room.Room
import com.kursivee.authentication.data.LoginCache
import com.kursivee.authentication.data.LoginService
import com.kursivee.authentication.domain.UserDaoUseCase
import com.kursivee.authentication.data.db.AppDatabase
import org.koin.dsl.module

object DataModule {
    val modules = module {
        single { Room.databaseBuilder(get(), AppDatabase::class.java, "users-db").build() }
        single { get<AppDatabase>().userDao() }
        single { LoginService() }
        single { LoginCache() }
        single { UserDaoUseCase(get()) }
    }
}
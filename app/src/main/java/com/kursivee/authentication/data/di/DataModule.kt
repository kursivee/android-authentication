package com.kursivee.authentication.data.di

import androidx.room.Room
import com.kursivee.authentication.domain.LoginCacheUseCase
import com.kursivee.authentication.data.LoginService
import com.kursivee.authentication.data.MemoryCache
import com.kursivee.authentication.domain.UserDaoUseCase
import com.kursivee.authentication.data.db.AppDatabase
import org.koin.dsl.module

object DataModule {
    val modules = module {
        single { Room.databaseBuilder(get(), AppDatabase::class.java, "users-db").build() }
        single { get<AppDatabase>().userDao() }
        single { LoginService() }
        single { MemoryCache() }
        single { UserDaoUseCase(get()) }
    }
}
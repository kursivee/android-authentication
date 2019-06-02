package com.kursivee.authentication.data.di

import androidx.room.Room
import com.kursivee.authentication.data.LoginApi
import com.kursivee.authentication.data.MemoryCache
import com.kursivee.authentication.data.db.AppDatabase
import org.koin.dsl.module

object DataModule {
    val modules = module {
        single { Room.databaseBuilder(get(), AppDatabase::class.java, "users-db").build() }
        single { get<AppDatabase>().userDao() }
        single { LoginApi() }
        single { MemoryCache() }
    }
}
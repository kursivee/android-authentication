package com.kursivee.authentication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kursivee.authentication.data.db.dao.UserDao
import com.kursivee.authentication.data.db.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
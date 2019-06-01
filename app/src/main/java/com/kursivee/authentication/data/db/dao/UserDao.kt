package com.kursivee.authentication.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kursivee.authentication.data.db.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1")
    fun findByUsername(username: String): User?

    @Query("DELETE FROM user")
    fun deleteAll()

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}
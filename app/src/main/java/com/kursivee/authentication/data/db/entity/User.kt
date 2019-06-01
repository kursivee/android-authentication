package com.kursivee.authentication.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val username: String,
    @ColumnInfo(name = "first_name") val firstName: String?
)
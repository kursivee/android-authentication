package com.kursivee.authentication.domain

import com.kursivee.authentication.data.db.dao.UserDao
import com.kursivee.authentication.data.db.entity.User

class UserDaoUseCase(private val userDao: UserDao) {

    fun cache(user: User) {
        userDao.insertAll(user)
    }

    fun clear() {
        userDao.deleteAll()
    }

    fun findByUsername(username: String): User? = userDao.findByUsername(username)
}
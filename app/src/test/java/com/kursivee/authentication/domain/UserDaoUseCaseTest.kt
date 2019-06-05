package com.kursivee.authentication.domain

import com.google.common.truth.Truth.assertThat
import com.kursivee.authentication.data.db.dao.UserDao
import com.kursivee.authentication.data.db.entity.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class UserDaoUseCaseTest {

    @Test
    fun `when caching a user it should cache the user`() {
        val userDao: UserDao = mockk(relaxUnitFun = true)
        val userDaoUseCase = UserDaoUseCase(userDao)
        userDaoUseCase.cache(User("username", "first"))
        verify(exactly = 1) { userDao.insertAll(any()) }
    }

    @Test
    fun `when clearing it should clear all the users`() {
        val userDao: UserDao = mockk(relaxUnitFun = true)
        val userDaoUseCase = UserDaoUseCase(userDao)
        userDaoUseCase.clear()
        verify(exactly = 1) { userDao.deleteAll() }
    }

    @Test
    fun `when searching for a user when user exists it should return a user`() {
        val userDao: UserDao = mockk(relaxUnitFun = true)
        val user = User("username", "first")
        every {
            userDao.findByUsername(any())
        } answers {
            user
        }
        val userDaoUseCase = UserDaoUseCase(userDao)
        assertThat(userDaoUseCase.findByUsername("username"))
            .isEqualTo(user)
    }

    @Test
    fun `when searching for a user when user does not exist it should return null`() {
        val userDao: UserDao = mockk(relaxUnitFun = true)
        every {
            userDao.findByUsername(any())
        } answers {
            null
        }
        val userDaoUseCase = UserDaoUseCase(userDao)
        assertThat(userDaoUseCase.findByUsername("username"))
            .isNull()
    }
}
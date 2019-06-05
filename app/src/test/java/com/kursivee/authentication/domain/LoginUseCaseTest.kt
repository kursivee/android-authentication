package com.kursivee.authentication.domain

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.kursivee.authentication.data.db.entity.User
import com.kursivee.authentication.data.response.ErrorResponse
import com.kursivee.authentication.data.response.NetworkResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LoginUseCaseTest {

    @Test
    fun `when logging in when authentication fails it should return an error response`() {
        val repository = mockk<LoginRepository>()
        coEvery {
            repository.authenticate(any())
        } coAnswers {
            NetworkResponse(error = ErrorResponse())
        }
        val loginUseCase = LoginUseCase(repository, mockk())
        runBlockingTest {
            loginUseCase.login("username").run {
                assertThat(response)
                    .isNotNull()
            }
        }
    }

    @Test
    fun `when logging in when authentication succeeds it should call authorization`() {
        val repository = mockk<LoginRepository>()
        coEvery {
            repository.authenticate(any())
        } coAnswers { NetworkResponse() }
        coEvery {
            repository.authorize(any())
        } coAnswers {
            NetworkResponse(error = ErrorResponse())
        }
        val loginUseCase = LoginUseCase(repository, mockk())
        runBlockingTest {
            loginUseCase.login("username").run {
                coVerify(exactly = 1) { repository.authorize(any()) }
            }
        }
    }

    @Test
    fun `when logging in when authentication succeeds when authorization fails it should return an error response`() {
        val repository = mockk<LoginRepository>()
        coEvery {
            repository.authenticate(any())
        } coAnswers {
            NetworkResponse()
        }
        coEvery {
            repository.authorize(any())
        } coAnswers {
            NetworkResponse(error = ErrorResponse())
        }
        val loginUseCase = LoginUseCase(repository, mockk())
        runBlockingTest {
            loginUseCase.login("username").run {
                assertThat(response)
                    .isNotNull()
            }
        }
    }

    @Test
    fun `when logging in when authentication succeeds when authorization succeeds when no cached user data it should create, cache, and return user`() {
        val repository = mockk<LoginRepository>()
        val cache = mockk<UserDaoUseCase>(relaxUnitFun = true)
        coEvery {
            repository.authenticate(any())
        } coAnswers {
            NetworkResponse()
        }
        coEvery {
            repository.authorize(any())
        } coAnswers {
            NetworkResponse()
        }

        coEvery {
            cache.findByUsername(any())
        } coAnswers {
            null
        }
        val loginUseCase = LoginUseCase(repository, cache)
        runBlockingTest {
            loginUseCase.login("username").run {
                coVerify(exactly = 1) { cache.cache(User("username", "FIRST")) }
            }
        }
    }

    @Test
    fun `when logging in when authentication succeeds when authorization succeeds when has cached user data it should return user`() {
        val repository = mockk<LoginRepository>()
        val cache = mockk<UserDaoUseCase>(relaxUnitFun = true)
        coEvery {
            repository.authenticate(any())
        } coAnswers {
            NetworkResponse()
        }
        coEvery {
            repository.authorize(any())
        } coAnswers {
            NetworkResponse()
        }
        coEvery {
            cache.findByUsername(any())
        } coAnswers {
            User("username", "FIRST")
        }
        val loginUseCase = LoginUseCase(repository, cache)
        runBlockingTest {
            loginUseCase.login("username").run {
                coVerify(exactly = 0) { cache.cache(any()) }
            }
        }
    }
}
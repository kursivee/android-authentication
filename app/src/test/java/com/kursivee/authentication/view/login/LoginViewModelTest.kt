package com.kursivee.authentication.view.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kursivee.authentication.data.LoginCache
import com.kursivee.authentication.data.response.ErrorResponse
import com.kursivee.authentication.data.response.NetworkResponse
import com.kursivee.authentication.domain.LoginUseCase
import com.kursivee.authentication.domain.UserDaoUseCase
import com.kursivee.util.CoroutinesTestRule
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val testRule = CoroutinesTestRule()

    @RelaxedMockK
    lateinit var observer: Observer<NetworkResponse<ErrorResponse>>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when login succeeds it should update the response observer`() =
        runBlockingTest {
            val loginUseCase = mockk<LoginUseCase>()
            coEvery {
                loginUseCase.login(any())
            } returns NetworkResponse()

            val vm = LoginViewModel(loginUseCase, mockk(), mockk())
            vm.login("username").observeForever(observer)
            verify { observer.onChanged(NetworkResponse()) }
        }

    @Test
    fun `when login fails it should update the response observer`() =
        runBlockingTest {
            val loginUseCase = mockk<LoginUseCase>()
            coEvery {
                loginUseCase.login(any())
            } returns NetworkResponse(ErrorResponse())

            val vm = LoginViewModel(loginUseCase, mockk(), mockk())
            vm.login("username").observeForever(observer)
            verify { observer.onChanged(NetworkResponse(ErrorResponse())) }
        }

    @Test
    fun `when clearing cache it should clear the login cache`() =
        runBlockingTest {
            val loginCache = mockk<LoginCache>(relaxUnitFun = true)
            val vm = LoginViewModel(mockk(), loginCache, mockk(relaxUnitFun = true))
            vm.clear()
            verify(exactly = 1) { loginCache.clear() }
        }

    @Test
    fun `when clearing cache it should clear the user cache`() =
        runBlockingTest {
            val userDaoUseCase = mockk<UserDaoUseCase>(relaxUnitFun = true)
            val vm = LoginViewModel(mockk(), mockk(relaxUnitFun = true), userDaoUseCase)
            vm.clear()
            verify(exactly = 1) { userDaoUseCase.clear() }
        }
}
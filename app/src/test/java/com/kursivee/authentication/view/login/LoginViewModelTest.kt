package com.kursivee.authentication.view.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kursivee.authentication.data.response.ErrorResponse
import com.kursivee.authentication.data.response.NetworkResponse
import com.kursivee.authentication.domain.LoginUseCase
import com.kursivee.util.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
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

}
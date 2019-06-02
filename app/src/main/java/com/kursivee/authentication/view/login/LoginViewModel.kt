package com.kursivee.authentication.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursivee.authentication.domain.LoginCache
import com.kursivee.authentication.domain.UserDaoUseCase
import com.kursivee.authentication.data.response.ErrorResponse
import com.kursivee.authentication.data.response.NetworkResponse
import com.kursivee.authentication.domain.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val loginCache: LoginCache,
    private val userCache: UserDaoUseCase
) : ViewModel() {

    // Artificial loading time to indicate change to user
    private val loadingTime: Long = 600

    private val mutableLoading = MutableLiveData<Boolean>()
    val loading = mutableLoading

    fun login(username: String): LiveData<NetworkResponse<ErrorResponse>> {
        mutableLoading.value = true
        val liveData = MutableLiveData<NetworkResponse<ErrorResponse>>()
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.login(username).run {
                delay(loadingTime)
                liveData.postValue(this)
                loading.postValue(false)
            }
        }
        return liveData
    }

    fun clear() {
        mutableLoading.value = true
        loginCache.clear()
        viewModelScope.launch(Dispatchers.IO) {
            userCache.clear()
            delay(loadingTime)
            loading.postValue(false)
        }
    }
}

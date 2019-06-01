package com.kursivee.authentication.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursivee.authentication.data.LoginCache
import com.kursivee.authentication.domain.UserDaoUseCase
import com.kursivee.authentication.data.response.ErrorResponse
import com.kursivee.authentication.data.response.NetworkResponse
import com.kursivee.authentication.domain.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val loginCache: LoginCache,
    private val userCache: UserDaoUseCase
) : ViewModel() {

    fun login(username: String): LiveData<NetworkResponse<ErrorResponse>> {
        val liveData = MutableLiveData<NetworkResponse<ErrorResponse>>()
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.login(username).run {
                liveData.postValue(this)
            }
        }
        return liveData
    }

    fun clear() {
        loginCache.clear()
        viewModelScope.launch(Dispatchers.IO) {
            userCache.clear()
        }
    }
}

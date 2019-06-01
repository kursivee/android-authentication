package com.kursivee.authentication.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursivee.authentication.data.LoginCache
import com.kursivee.authentication.data.response.ErrorResponse
import com.kursivee.authentication.data.response.NetworkResponse
import com.kursivee.authentication.domain.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    fun login(username: String): LiveData<NetworkResponse<ErrorResponse>> {
        val liveData = MutableLiveData<NetworkResponse<ErrorResponse>>()
        viewModelScope.launch {
            loginUseCase.login(username).run {
                liveData.postValue(this)
            }
        }
        return liveData
    }
}

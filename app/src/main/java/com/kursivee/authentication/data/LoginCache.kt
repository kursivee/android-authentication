package com.kursivee.authentication.data

import com.kursivee.authentication.data.response.AuthenticationResponse
import com.kursivee.authentication.data.response.AuthorizeResponse
import com.kursivee.authentication.data.response.NetworkResponse

class LoginCache {
    var authenticationCache: NetworkResponse<AuthenticationResponse>? = null
    var authorizationCache: NetworkResponse<AuthorizeResponse>? = null
}
package com.kursivee.authentication.data

import com.kursivee.authentication.data.response.AuthetnicateResponse
import com.kursivee.authentication.data.response.AuthorizeResponse
import com.kursivee.authentication.data.response.NetworkResponse

class LoginCache {
    var authenticationCache: NetworkResponse<AuthetnicateResponse>? = null
    var authorizationCache: NetworkResponse<AuthorizeResponse>? = null
}
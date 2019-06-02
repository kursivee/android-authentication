package com.kursivee.authentication.domain

import com.kursivee.authentication.data.MemoryCache
import com.kursivee.authentication.data.response.AuthenticationResponse
import com.kursivee.authentication.data.response.AuthorizeResponse
import com.kursivee.authentication.data.response.NetworkResponse

class LoginCache(private val memoryCache: MemoryCache) {
    fun clear() {
        memoryCache.remove(AuthenticationResponse::class.java)
        memoryCache.remove(AuthorizeResponse::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    fun getAuthnResponse(): NetworkResponse<AuthenticationResponse>? =
        memoryCache.get(AuthenticationResponse::class.java) as? NetworkResponse<AuthenticationResponse>

    fun putAuthnResponse(authenticationResponse: NetworkResponse<AuthenticationResponse>) {
        memoryCache.put(AuthenticationResponse::class.java, authenticationResponse)
    }

    @Suppress("UNCHECKED_CAST")
    fun getAuthzResponse(): NetworkResponse<AuthorizeResponse>? =
        memoryCache.get(AuthorizeResponse::class.java) as? NetworkResponse<AuthorizeResponse>

    fun putAuthzResponse(authorizeResponse: NetworkResponse<AuthorizeResponse>) {
        memoryCache.put(AuthorizeResponse::class.java, authorizeResponse)
    }
}

package com.kursivee.authentication.view.authentication

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task

class GoogleAuthentication(private val token: String) {

    companion object {
        const val RC_SIGN_IN = 1
    }

    fun getSignInIntent(activity: Activity): Intent =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .build().run {
                GoogleSignIn.getClient(activity, this).signInIntent
            }

    fun getSignedInAccount(intent: Intent): Task<GoogleSignInAccount> =
        GoogleSignIn.getSignedInAccountFromIntent(intent)
}
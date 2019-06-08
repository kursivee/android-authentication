package com.kursivee.authentication.view.google

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.kursivee.authentication.view.login.LoginFragment

class AuthenticationDelegate(private val token: String, private val onFirebaseAuthComplete: OnCompleteListener<AuthResult>, private val activity: Activity) {

    private val firebaseAuthentication: FirebaseAuthentication by lazy {
        FirebaseAuthentication()
    }

    private val googleAuthentication: GoogleAuthentication by lazy {
        GoogleAuthentication(token)
    }

    var currentUser = firebaseAuthentication.currentUser
        private set

    fun authenticate(): Intent = googleAuthentication.getSignInIntent(activity)

    fun authenticate(intent: Intent) {
        val task = googleAuthentication.getSignedInAccount(intent)
        try {
            task.getResult(ApiException::class.java)?.let { account ->
                firebaseAuthentication.authenticate(account, onFirebaseAuthComplete)
            }
        } catch (t: Throwable) {
            Log.e(LoginFragment::class.java.simpleName, "Google sign in failed", t)
        }
    }
}
package com.kursivee.authentication.view.authentication

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kursivee.authentication.view.login.LoginFragment

class AuthenticationDelegate(private val clientId: String) {

    companion object {
        const val RC_SIGN_IN = 1
    }

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val currentUser = firebaseAuth.currentUser

    fun authenticate(activity: Activity): Intent =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build().run {
                GoogleSignIn.getClient(activity, this).signInIntent
            }

    fun authenticate(intent: Intent): Task<AuthResult>? {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        try {
            task.getResult(ApiException::class.java)?.let { account ->
               return firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))
            }
        } catch (t: Throwable) {
            Log.e(LoginFragment::class.java.simpleName, "Google sign in failed", t)
        }
        return null
    }
}

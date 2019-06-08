package com.kursivee.authentication.view.authentication

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kursivee.authentication.view.login.LoginFragment

class AuthenticationDelegate(private val clientId: String, private val activity: Activity) {

    companion object {
        const val RC_SIGN_IN = 1
    }

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private lateinit var googleSignInClient: GoogleSignInClient

    var currentUser = firebaseAuth.currentUser
        private set

    fun authenticate(): Intent =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build().run {
                googleSignInClient = GoogleSignIn.getClient(activity, this)
                googleSignInClient.signInIntent
            }

    fun authenticate(intent: Intent, onComplete: (AuthResult) -> Unit = {}) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        try {
            task.getResult(ApiException::class.java)?.let { account ->
               firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))
                   .addOnCompleteListener {
                       it.result?.let { result ->
                           onComplete(result)
                       }
                   }
            }
        } catch (t: Throwable) {
            Log.e(LoginFragment::class.java.simpleName, "Google sign in failed", t)
        }
    }

    fun signOut(): Task<Void> {
        firebaseAuth.signOut()
        if(!::googleSignInClient.isInitialized) {
            authenticate()
        }
        return googleSignInClient.signOut()
    }
}

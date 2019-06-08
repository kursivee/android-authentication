package com.kursivee.authentication.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kursivee.authentication.R
import com.kursivee.authentication.view.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.kursivee.authentication.view.authentication.AuthenticationDelegate
import com.kursivee.authentication.view.util.AlertFactory
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class LoginFragment : Fragment(), OnCompleteListener<AuthResult> {

    companion object {
        fun newInstance() = LoginFragment()
    }

    val alertFactory: AlertFactory by inject()

    private val progressBarComponent by lazy {
        (requireActivity() as MainActivity).progressBarComponent
    }

    private val vm: LoginViewModel by viewModel()

    private lateinit var authDelegate: AuthenticationDelegate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState).also {
            authDelegate = AuthenticationDelegate(get(), activity!!)
            view.findViewById<SignInButton>(R.id.btn_sign_in).setOnClickListener {
                signIn()
            }
            view.findViewById<Button>(R.id.btn_sign_out).setOnClickListener {
                signOut()
            }
            SignInComponent(view.findViewById(R.id.cl_login), this, vm)
            vm.loading.observe(this, Observer {
                if(it) {
                    progressBarComponent.show()
                } else {
                    progressBarComponent.dismiss()
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AuthenticationDelegate.RC_SIGN_IN) {
            data?.let {
                progressBarComponent.show()
                authDelegate.authenticate(it) { result ->
                    progressBarComponent.dismiss()
                    alertFactory.toast("Hello ${result.user.displayName}").show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        authDelegate.currentUser?.let {
            alertFactory.toast("Hello ${it.displayName}").show()
        }
    }

    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            alertFactory.toast("Signed In to firebase").show()
        } else {
            alertFactory.toast("Firebase auth failed :(").show()
        }
    }

    private fun signIn() {
        startActivityForResult(authDelegate.authenticate(), AuthenticationDelegate.RC_SIGN_IN)
    }

    private fun signOut() {
        progressBarComponent.show()
        authDelegate.signOut().addOnCompleteListener {
            progressBarComponent.dismiss()
            alertFactory.toast("Signed out").show()
        }
    }
}

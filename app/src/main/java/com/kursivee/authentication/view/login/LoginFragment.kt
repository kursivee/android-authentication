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
import android.widget.Toast
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.kursivee.authentication.view.authentication.AuthenticationDelegate
import org.koin.android.ext.android.inject

class LoginFragment : Fragment(), OnCompleteListener<AuthResult> {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val progressBarComponent by lazy {
        (requireActivity() as MainActivity).progressBarComponent
    }

    private val vm: LoginViewModel by viewModel()

    val authDelegate: AuthenticationDelegate by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState).also {
            view.findViewById<SignInButton>(R.id.btn_sign_in).setOnClickListener {
                signIn()
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
                authDelegate.authenticate(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        authDelegate.currentUser?.let {
            Toast.makeText(context,"Hello ${it.displayName}",Toast.LENGTH_LONG).show()
        }
    }

    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            Toast.makeText(context, "Signed In to firebase", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Firebase auth failed :(", Toast.LENGTH_LONG).show()
        }
    }

    private fun signIn() {
        activity?.let {
            startActivityForResult(authDelegate.authenticate(it), 1)
        }
    }
}

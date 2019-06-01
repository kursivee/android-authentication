package com.kursivee.authentication.view.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.kursivee.authentication.R
import com.kursivee.authentication.util.Usernames
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState).also {
            btn_fail_authn.setOnClickListener {
                login(Usernames.FAIL_AUTHN.toString())
            }
            btn_fail_authz.setOnClickListener {
                login(Usernames.FAIL_AUTHZ.toString())
            }
            btn_success.setOnClickListener {
                login(Usernames.SUCCESS.toString())
            }
        }
    }

    private fun login(username: String) {
        viewModel.login(username).observe(this, Observer {
            it.response?.let { response ->
                tv_message.text = response.error
            } ?: run {
                tv_message.text = "SUCCESS"
            }
        })
    }
}

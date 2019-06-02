package com.kursivee.authentication.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kursivee.authentication.R
import com.kursivee.authentication.util.Usernames
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val loginActivity by lazy { requireActivity() as LoginActivity }

    private val vm: LoginViewModel by viewModel()

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
            btn_clear_cache.setOnClickListener {
                vm.clear()
            }
        }
    }

    private fun login(username: String) {
        loginActivity.showProgressBar()
        vm.login(username).observe(this, Observer {
            it.response?.let { response ->
                tv_message.text = response.error
            } ?: run {
                tv_message.text = "SUCCESS"
            }
            loginActivity.dismissProgressBar()
        })
    }
}

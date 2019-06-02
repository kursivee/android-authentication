package com.kursivee.authentication.view.login

import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.kursivee.authentication.R
import com.kursivee.authentication.util.Usernames
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*

class SignInComponent(
    private val container: ViewGroup,
    private val owner: LifecycleOwner,
    private val viewModel: LoginViewModel
) {
    private val btnSuccess: Button by lazy {
        container.findViewById<Button>(R.id.btn_success)
    }

    private val btnFailAuthn: Button by lazy {
        container.findViewById<Button>(R.id.btn_fail_authn)
    }

    private val btnFailAuthz: Button by lazy {
        container.findViewById<Button>(R.id.btn_fail_authz)
    }

    private val btnClearCache: Button by lazy {
        container.findViewById<Button>(R.id.btn_clear_cache)
    }

    private val tvMessage: TextView by lazy {
        container.findViewById<TextView>(R.id.tv_message)
    }

    init {
        btnFailAuthn.setOnClickListener {
            login(Usernames.FAIL_AUTHN.toString())
        }
        btnFailAuthz.setOnClickListener {
            login(Usernames.FAIL_AUTHZ.toString())
        }
        btnSuccess.setOnClickListener {
            login(Usernames.SUCCESS.toString())
        }
        btnClearCache.setOnClickListener {
            viewModel.clear()
        }
    }

    private fun login(username: String) {
        viewModel.login(username).observe(owner, Observer {
            it.response?.let { response ->
                tvMessage.text = response.error
            } ?: run {
                tvMessage.text = "SUCCESS"
            }
        })
    }
}
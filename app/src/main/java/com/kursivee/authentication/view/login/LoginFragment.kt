package com.kursivee.authentication.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kursivee.authentication.R
import com.kursivee.authentication.util.Usernames
import com.kursivee.authentication.view.main.MainActivity
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val progressBarComponent by lazy {
        (requireActivity() as MainActivity).progressBarComponent
    }

    private val vm: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState).also {
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
}

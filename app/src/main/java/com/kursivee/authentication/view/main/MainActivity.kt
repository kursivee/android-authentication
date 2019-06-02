package com.kursivee.authentication.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kursivee.authentication.R
import com.kursivee.authentication.view.login.LoginFragment
import kotlinx.android.synthetic.main.login_activity.*

class MainActivity : AppCompatActivity() {

    lateinit var progressBarComponent: MainProgressBarComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
        progressBarComponent = MainProgressBarComponent(cl_loading)
    }
}
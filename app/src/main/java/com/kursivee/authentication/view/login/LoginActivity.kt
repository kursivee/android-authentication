package com.kursivee.authentication.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.kursivee.authentication.R
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {

    private lateinit var progressBarJob: ProgressBarJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }

    fun showProgressBar() {
        cl_loading?.also {
            it.isVisible = true
            it.bringToFront()
        }
        progressBarJob = ProgressBarJob(pb_loading).apply { start() }
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun dismissProgressBar() {
        progressBarJob.stop()
        cl_loading.isGone = true
        window?.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}
package com.kursivee.authentication.view.main

import android.app.Activity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.kursivee.authentication.R
import com.kursivee.authentication.view.progressbar.ProgressBarJob

class MainProgressBarComponent(private val view: ViewGroup) {

    private lateinit var progressBarJob: ProgressBarJob

    private val clLoading by lazy {
        view.findViewById<ConstraintLayout>(R.id.cl_loading)
    }

    private val pbLoading by lazy {
        view.findViewById<ProgressBar>(R.id.pb_loading)
    }

    fun show() {
        clLoading.also {
            it.isVisible = true
            it.bringToFront()
        }
        progressBarJob = ProgressBarJob(pbLoading).apply { start() }
        (view.context as Activity).window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun dismiss() {
        progressBarJob.stop()
        clLoading.isGone = true
        (view.context as Activity).window?.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}
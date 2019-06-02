package com.kursivee.authentication.view.main

import android.app.Activity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.annotation.VisibleForTesting
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.kursivee.authentication.R
import com.kursivee.authentication.view.progressbar.ProgressBarJob

class MainProgressBarComponent(private val container: ViewGroup, private val progressBarJob: ProgressBarJob) {

    companion object {
        const val PB_CONTAINER_ID = R.id.cl_loading.toString()
    }

    private val pbLoading by lazy {
        container.findViewById<ProgressBar>(R.id.pb_loading)
    }

    fun show() {
        container.also {
            it.isVisible = true
            it.bringToFront()
        }
        pbLoading.isVisible = true
        progressBarJob.start(pbLoading)
        (container.context as Activity).window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun dismiss() {
        progressBarJob.stop()
        container.isGone = true
        pbLoading.isGone = true
        (container.context as Activity).window?.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}
package com.kursivee.authentication.view.progressbar

import android.widget.ProgressBar

class ProgressBarJobFactory {
    fun getProgressBarJob(progressBar: ProgressBar) = ProgressBarJob(progressBar)
}
package com.kursivee.authentication.view.progressbar

import android.widget.ProgressBar
import kotlinx.coroutines.*

class ProgressBarJob {
    private lateinit var scope: CoroutineScope
    private var progressBar: ProgressBar? = null

    fun start(progressBar: ProgressBar) {
        this.progressBar = progressBar
        scope = CoroutineScope(Dispatchers.Main + Job())
        scope.launch {
            while(true) {
                with(progressBar) {
                    progress += 1
                    delay(10)
                    if(progress == 200) {
                        progress = 0
                    }
                }
            }
        }
    }

    fun stop() {
        progressBar?.progress = 0
        progressBar = null
        scope.cancel()
    }
}
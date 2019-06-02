package com.kursivee.authentication.view.login

import android.widget.ProgressBar
import kotlinx.coroutines.*

class ProgressBarJob(private val progressBar: ProgressBar) {
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    fun start() {
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
        progressBar.progress = 0
        scope.cancel()
    }
}
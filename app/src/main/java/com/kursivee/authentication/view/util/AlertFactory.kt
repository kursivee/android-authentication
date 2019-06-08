package com.kursivee.authentication.view.util

import android.content.Context
import android.widget.Toast

class AlertFactory(private val context: Context) {
    fun toast(message: String, length: Int = Toast.LENGTH_LONG): Toast =
            Toast.makeText(context, message, length)
}

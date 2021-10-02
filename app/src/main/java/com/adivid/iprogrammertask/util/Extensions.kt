package com.adivid.iprogrammertask.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

fun ProgressBar.showProgressBar(b: Boolean) {
    if (b) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
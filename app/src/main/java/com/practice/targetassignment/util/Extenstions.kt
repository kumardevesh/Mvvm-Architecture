package com.practice.targetassignment.util

import android.view.View

fun View.show() {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}


fun View.gone() {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}
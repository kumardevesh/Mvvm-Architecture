package com.practice.targetassignment.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

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

fun ImageView.loadCircularImage(url:String?){
    Glide.with(this.context).load(url).apply(RequestOptions.circleCropTransform())
        .into(this)
}
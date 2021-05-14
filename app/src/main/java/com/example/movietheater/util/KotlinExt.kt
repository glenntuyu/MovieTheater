package com.example.movietheater.util

import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun TextView.setTextAndCheckShow(text: String?) {
    if (text.isNullOrEmpty()) {
        gone()
    } else {
        setText(text)
        visible()
    }
}

fun Date.toSimpleString() : String {
    val format = SimpleDateFormat("MMM dd yyyy")
    return format.format(this)
}
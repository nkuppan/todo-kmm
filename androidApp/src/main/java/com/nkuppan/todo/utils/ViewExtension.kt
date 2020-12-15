package com.nkuppan.todo.utils

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:setStrike")
fun setStrike(aView: TextView, aStatus: Boolean) {
    if (aStatus) {
        aView.paintFlags = aView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}

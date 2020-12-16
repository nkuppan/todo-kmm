package com.nkuppan.todo.utils

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.ThemeUtils
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.nkuppan.todo.R

@BindingAdapter("app:setStrike")
fun setStrike(aView: TextView, aStatus: Boolean) {
    if (aStatus) {
        aView.paintFlags = aView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}

@SuppressLint("RestrictedApi")
@BindingAdapter("app:setSelection")
fun setStrike(aView: View, aId: String) {
    aView.setBackgroundColor(
        if (SettingPrefManager.getSelectedTaskGroup() == aId) {
            ContextCompat.getColor(aView.context, R.color.grey_light)
        } else {
            ThemeUtils.getThemeAttrColor(aView.context, R.attr.colorSurface)
        }
    )
}

package com.nkuppan.todo.extention

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.ThemeUtils
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.nkuppan.todo.R
import com.nkuppan.todo.utils.SettingPrefManager


@BindingAdapter("app:setStrike")
fun setStrike(aView: TextView, aStatus: Boolean) {
    if (aStatus) {
        aView.paintFlags = aView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}

@BindingAdapter("app:setSelected")
fun setSelected(aView: TextView, aStatus: Boolean) {
    aView.setCompoundDrawablesWithIntrinsicBounds(
        null,
        null,
        if (aStatus) ContextCompat.getDrawable(aView.context, R.drawable.ic_done) else null,
        null
    )
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

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).run {
        show()
    }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupStringSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<String>>,
    timeLength: Int
) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            showSnackbar(it, timeLength)
        }
    })
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Int>>,
    timeLength: Int
) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            showSnackbar(context.getString(it), timeLength)
        }
    })
}
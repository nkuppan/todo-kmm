package com.nkuppan.todo.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.nkuppan.todo.ui.fragment.DateTimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

object AppUIUtils {

    fun showDatePickerDialog(
        aFragment: Fragment,
        aDateTime: Long? = null,
        aCallback: ((Long) -> Unit)?
    ) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, aYear, aMonth, aDay ->
            val calendar = Calendar.getInstance()
            calendar.set(aYear, aMonth, aDay)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            aCallback?.invoke(calendar.timeInMillis)
        }

        DateTimePickerFragment().apply {
            aDateTime?.let {
                arguments = Bundle().apply {
                    putLong(RequestParam.DATE_TIME, aDateTime)
                }
            }
            this.dateSetListener = dateSetListener
            show(aFragment.childFragmentManager, "date_picker")
        }
    }

    fun showTimePickerDialog(aFragment: Fragment, aCallback: ((Long) -> Unit)?) {

        val timePickerListener =
            TimePickerDialog.OnTimeSetListener { _: TimePicker, aHourOfDay: Int, aMinute: Int ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, aHourOfDay)
                calendar.set(Calendar.MINUTE, aMinute)
                aCallback?.invoke(calendar.timeInMillis)
            }

        DateTimePickerFragment().apply {
            this.timePickerListener = timePickerListener
            show(aFragment.childFragmentManager, "time_picker")
        }
    }

    fun getTaskEndDate(aTaskEndDate: Double?): String {

        aTaskEndDate ?: return ""

        val newDate = Date()
        newDate.time = aTaskEndDate.toLong()
        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return simpleDateFormat.format(newDate)
    }
}
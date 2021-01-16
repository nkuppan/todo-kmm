package com.nkuppan.todo.ui.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.nkuppan.todo.utils.RequestParam
import java.util.*

class DateTimePickerFragment : DialogFragment(),
    TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    private var isDatePicker = true

    var dateSetListener: DatePickerDialog.OnDateSetListener? = null

    var timePickerListener: TimePickerDialog.OnTimeSetListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isDatePicker = dateSetListener != null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = arguments?.getLong(RequestParam.DATE_TIME)
            ?: System.currentTimeMillis()

        return if (isDatePicker) {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(requireContext(), this, year, month, day)
        } else {
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
        }
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        timePickerListener?.onTimeSet(view, hourOfDay, minute)
    }

    override fun onDateSet(view: DatePicker?, date: Int, month: Int, year: Int) {
        dateSetListener?.onDateSet(view, date, month, year)
    }
}
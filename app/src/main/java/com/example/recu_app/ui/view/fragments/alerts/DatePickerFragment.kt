package com.example.recu_app.ui.view.fragments.alerts

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.util.Calendar

class DatePickerFragment(private val deadLineListener: (String) -> Unit) : DialogFragment(),
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var calendar: Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        }
        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day

        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        calendar[Calendar.HOUR_OF_DAY] = hourOfDay
        calendar[Calendar.MINUTE] = minute

        val dateString: String = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(calendar.time)
        deadLineListener(dateString)
    }
}

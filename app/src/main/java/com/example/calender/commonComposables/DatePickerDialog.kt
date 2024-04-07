package com.example.calender.commonComposables

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerDialog(
    minDate: Long? = null,
    maxDate: Long? = null,
    initialDate: Date = remember { Date() },
    dismissListener: () -> Unit,
    onDateChange: (year: Int, month: Int, dayOfMonth: Int) -> Unit
) {
    val context = LocalContext.current
    DisposableEffect(
        key1 = Unit,
        effect = {
            val initialLocalDate = localDateFor(initialDate)
            val initialYear = initialLocalDate.year
            val initialMonth = initialLocalDate.monthValue - 1
            val initialDayOfMonth = initialLocalDate.dayOfMonth
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year: Int, month: Int, dayOfMonth: Int ->
                    onDateChange(year, month, dayOfMonth)
                },
                initialYear, initialMonth, initialDayOfMonth
            ).apply {
                setCancelable(false)
                setOnDismissListener { dismissListener() }
                datePicker.apply {
                    minDate?.let {
                        this.minDate = it

                    }
                    maxDate?.let {
                        this.maxDate = it
                    }
                }
            }
            datePickerDialog.show()
            onDispose {
                datePickerDialog.hide()
            }
        }
    )
}

private fun localDateFor(date: Date): LocalDate {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

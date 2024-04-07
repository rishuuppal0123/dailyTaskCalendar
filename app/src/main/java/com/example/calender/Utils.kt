package com.example.calender

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)
}
package com.example.calender.commonComposables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.calender.showToast

@Composable
fun Toast(key: Any = Unit, msg: String?) {
    val context = LocalContext.current
    LaunchedEffect(key1 = key, block = { context.showToast(message = msg) })
}
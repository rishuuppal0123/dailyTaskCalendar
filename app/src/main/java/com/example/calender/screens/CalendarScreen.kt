package com.example.calender.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calender.R
import com.example.calender.commonComposables.HorizontalDivider
import com.example.calender.commonComposables.customClickable
import java.time.LocalDate
import java.time.ZoneOffset


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(toTaskScreen: () -> Unit, toStoreTask: (date: Long) -> Unit) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showMonthYearChooser by  remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calendar",
            style = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
            )
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                showMonthYearChooser = !showMonthYearChooser
            }) {
                Text(text = "Go To")
            }
            Button(onClick = { toTaskScreen() }) {
                Text(text = "View Tasks")
            }
        }

        HorizontalDivider()

        CalendarView(
            showMonthYearChooser = showMonthYearChooser,
            selectedDate = selectedDate,
            onDateSelected = {
                selectedDate = it
            })

        HorizontalDivider()

        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Daily Tasks", fontSize = 20.sp, fontWeight = FontWeight.Medium)
            Icon(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .customClickable {
                        toStoreTask(
                            selectedDate
                                .atStartOfDay(ZoneOffset.UTC)
                                .toInstant()
                                .toEpochMilli()
                        )
                    }
            )
        }
    }
}

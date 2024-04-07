package com.example.calender.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calender.R
import com.example.calender.commonComposables.HorizontalDivider
import com.example.calender.commonComposables.customClickable
import com.example.calender.commonComposables.`if`
import com.example.calender.isLeapYear
import com.example.calender.ui.theme.Purple40
import java.time.LocalDate
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(
    showMonthYearChooser: Boolean,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {

    val currentMonth = remember { mutableStateOf(selectedDate.month) }
    val currentYear = remember { mutableStateOf(selectedDate.year) }
    val startDate = remember {
        mutableStateOf(LocalDate.of(
            currentYear.value,
            currentMonth.value,
            selectedDate.dayOfMonth
        ))
    }

    fun previousMonth() {
        if(currentMonth.value == Month.JANUARY) {
            currentMonth.value = Month.DECEMBER
            currentYear.value -= 1
        }
        else {
            currentMonth.value -= 1
        }
        startDate.value =
            LocalDate.of(
                currentYear.value,
                currentMonth.value,
                startDate.value.dayOfMonth
            )
    }

    fun nextMonth() {
        if(currentMonth.value == Month.DECEMBER) {
            currentMonth.value = Month.JANUARY
            currentYear.value += 1
        }
        else {
            currentMonth.value += 1
        }
        startDate.value =
            LocalDate.of(
                currentYear.value,
                currentMonth.value,
                startDate.value.dayOfMonth
            )
    }
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if(showMonthYearChooser) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SelectYear(modifier = Modifier.weight(1f), currentYear = currentYear) {
                    currentYear.value = it
                    startDate.value = LocalDate.of(
                        currentYear.value,
                        currentMonth.value,
                        startDate.value.dayOfMonth
                    )
                }
                SelectMonth(modifier = Modifier.weight(1f), currentMonth = currentMonth) { it ->
                    currentMonth.value = it
                    startDate.value =
                        LocalDate.of(
                            currentYear.value,
                            currentMonth.value,
                            startDate.value.dayOfMonth
                        )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if(currentYear.value!=2001 || currentMonth.value!=Month.JANUARY) {
                Image(
                    painter = painterResource(id = R.drawable.left_arrow),
                    contentDescription = "left",
                    modifier = Modifier
                        .size(20.dp)
                        .customClickable {
                            previousMonth()
                        }
                )
            }
            Text(
                text = "${currentMonth.value}, ${currentYear.value}",
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            )
            if(currentYear.value != 2050 || currentMonth.value != Month.DECEMBER) {
                Image(
                    painter = painterResource(id = R.drawable.right_arrow),
                    contentDescription = "right",
                    modifier = Modifier
                        .size(20.dp)
                        .customClickable {
                            nextMonth()
                        }
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            val days = listOf("Sun", "Mon", "Tue", "Wed", "Thurs", "Fri", "Sat")
            for (i in 0 until 7) {
                Text(
                    text = days[i],
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.LightGray)
                        .padding(vertical = 4.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        CalendarGrid(startDate.value, currentMonth.value) {
            startDate.value = it
            onDateSelected(it)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun CalendarGrid(
    selectedDate: LocalDate,
    currentMonth: Month?,
    onDateSelected: (LocalDate) -> Unit
) {

    val daysInMonth = currentMonth?.length(isLeapYear(selectedDate.year))
    val firstDayOfMonth = LocalDate.of(selectedDate.year, currentMonth, 1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val totalCells = daysInMonth?.plus(firstDayOfWeek)

    val days = (1..totalCells!!).map {
        val day = it - firstDayOfWeek
        if (day <= 0) null else LocalDate.of(selectedDate.year, currentMonth, day)
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (i in 0 until 6) {
            Row {
                for (j in 0 until 7) {
                    val index = i * 7 + j
                    val day = days.getOrNull(index)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .`if`(
                                day == selectedDate,
                                then = { it.border(color = Purple40, width = 2.dp, shape = CircleShape) })
                            .background(Color.White, shape = CircleShape)
                            .customClickable {
                                day?.let { onDateSelected(it) }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        day?.let {
                            Text(
                                modifier = Modifier.padding(vertical = 12.dp),
                                fontWeight = FontWeight.Medium,
                                text = it.dayOfMonth.toString(),
                                textAlign = TextAlign.Center,
                                style = TextStyle(fontSize = 16.sp),
                                color = if (it == selectedDate) Purple40 else Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SelectYear(modifier: Modifier, currentYear: MutableState<Int>, onChange: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.background(color = Color.LightGray)
    ) {
        var yearExpanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .customClickable {
                    yearExpanded = !yearExpanded
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${currentYear.value}",
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_dropdown_grey),
                    contentDescription = null
                )
            }
        }
        DropdownMenu(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                ),
            expanded = yearExpanded,
            onDismissRequest = { yearExpanded = false },
            offset = DpOffset(20.dp, 20.dp)
        ) {
            for (year in 2001..2050) {
                DropdownMenuItem(
                    text = { Text(text = year.toString()) },
                    onClick = {
                        onChange(year)
                        yearExpanded = false
                    })
                if (year != 2050) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectMonth(modifier: Modifier, currentMonth: MutableState<Month>, onChange: (Month) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.background(color = Color.LightGray)
    ) {
        var monthExpanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .customClickable {
                    monthExpanded = !monthExpanded
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${currentMonth.value}",
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_dropdown_grey),
                    contentDescription = null
                )
            }
        }
        DropdownMenu(
            expanded = monthExpanded,
            onDismissRequest = { monthExpanded = false },
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            for (month in Month.values()) {
                DropdownMenuItem(
                    text = { Text(text = month.toString()) },
                    onClick = {
                        onChange(month)
                        monthExpanded = false
                    })
            }
        }
    }
}

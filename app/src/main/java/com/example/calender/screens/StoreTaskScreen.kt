package com.example.calender.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calender.commonComposables.ErrorLayout
import com.example.calender.commonComposables.PrimaryTextField
import com.example.calender.viewModels.StoreTaskViewModel

@Composable
fun StoreTaskScreen(
    date: Long,
    taskAddedAction: () -> Unit,
    viewModel: StoreTaskViewModel = hiltViewModel()
) {
    val state by viewModel.uiState
    if (state.success) {
        LaunchedEffect(key1 = Unit, block = { taskAddedAction() })
    }
    if (state.error.isNotBlank()) {
        ErrorLayout(message = state.error, btnText = "Back") {
            taskAddedAction()
        }
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .shadow(8.dp, shape = RoundedCornerShape(4.dp), ambientColor = Color.DarkGray)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
            .background(Color.White)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add your task",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = FontFamily.Cursive
        )

        PrimaryTextField(
            error = state.titleError.isNotBlank(),
            helperText = state.titleError,
            placeHolder = "Enter task title here",
            label = "Title",
            value = state.task.title,
            onValueChange = {
                viewModel.editTaskTitle(title = it)
            })

        PrimaryTextField(
            placeHolder = "Enter task body here",
            label = "Task Description",
            value = state.task.description,
            onValueChange = {
                viewModel.editTaskDescription(desc = it)
            })

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.addTask(createdDate = date)
            },
            enabled = state.task.title.isNotEmpty()
        ) {
            Text(
                text = "Add",
                color = Color.White,
                modifier = Modifier.padding(vertical = 6.dp),
                fontSize = 18.sp
            )
        }
    }
}
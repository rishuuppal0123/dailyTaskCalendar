package com.example.calender.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calender.R
import com.example.calender.commonComposables.ErrorLayout
import com.example.calender.data.Task
import com.example.calender.viewModels.TaskListViewModel

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else if (state.error.isNotBlank()) {
            ErrorLayout(message = state.error) {
                viewModel.getTasks()
            }
        } else {
            Text(
                text = "My Tasks",
                fontStyle = FontStyle.Italic,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 12.dp)
            )
            if (state.tasks.isEmpty()) {
                Text(
                    text = "No Tasks found!",
                    modifier = Modifier.padding(top = 20.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(items = state.tasks, itemContent = { task ->
                        TaskItem(task = task) {
                            viewModel.deleteTask(task_id = task.task_id)
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, deleteAction: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Cyan.copy(0.4f), shape = RoundedCornerShape(4.dp))
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(4.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = task.task_detail.title,
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Image(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "deleteIcon",
                modifier = Modifier
                    .size(20.dp)
                    .clickable { deleteAction() }
            )
        }
        Text(
            text = task.task_detail.description,
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}


package com.example.calender.data

data class TaskListResponse(
    val tasks: List<Task>
)

data class TaskModel(
    val title: String = "",
    val description: String = "",
    val created_date: Long = 0L
)

data class Task(
    val task_id: Int,
    val task_detail: TaskModel
)
package com.example.calender.repo

import com.example.calender.common.ApiResult
import com.example.calender.data.ManageTaskResponse
import com.example.calender.data.TaskListResponse

interface TaskListRepository {


    suspend fun getTasks(body: MutableMap<String, Any?>): ApiResult<TaskListResponse>
    suspend fun deleteTask(body: MutableMap<String, Any?>): ApiResult<ManageTaskResponse>
    suspend fun addTask(body: MutableMap<String, Any?>): ApiResult<ManageTaskResponse>
}
package com.example.calender.repo

import com.example.calender.common.ApiResult
import com.example.calender.data.ManageTaskResponse
import com.example.calender.data.Task
import com.example.calender.data.TaskListResponse

interface TaskListRepository {

//    private val taskListApi: TaskListApi by lazy {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://dev.frndapp.in:8085/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        retrofit.create(TaskListApi::class.java)
//    }

    suspend fun getTasks(body: MutableMap<String, Any?>): ApiResult<TaskListResponse>
    suspend fun deleteTask(body: MutableMap<String, Any?>): ApiResult<ManageTaskResponse>
    suspend fun addTask(body: MutableMap<String, Any?>): ApiResult<ManageTaskResponse>
}
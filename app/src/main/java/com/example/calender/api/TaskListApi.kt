package com.example.calender.api

import com.example.calender.data.ManageTaskResponse
import com.example.calender.data.TaskListResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskListApi {

    @POST("api/getCalendarTaskList")
    suspend fun getTasks(@Body body: MutableMap<String, Any?>): TaskListResponse

    @POST("api/storeCalendarTask")
    suspend fun addTask(@Body body: MutableMap<String, Any?>): ManageTaskResponse

    @POST("api/deleteCalendarTask")
    suspend fun deleteTask(@Body body: MutableMap<String, Any?>): ManageTaskResponse
}
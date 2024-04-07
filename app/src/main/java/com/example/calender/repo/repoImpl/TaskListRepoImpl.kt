package com.example.calender.repo.repoImpl

import com.example.calender.api.TaskListApi
import com.example.calender.common.ApiResult
import com.example.calender.common.ApiService
import com.example.calender.data.ManageTaskResponse
import com.example.calender.data.TaskListResponse
import com.example.calender.repo.TaskListRepository
import javax.inject.Inject

class TaskListRepoImpl @Inject constructor(
    private val apiService: ApiService
) : TaskListRepository {
    private val service = apiService.buildService(TaskListApi::class.java)

    override suspend fun getTasks(body: MutableMap<String, Any?>): ApiResult<TaskListResponse> {
        return apiService.getApiResult {
            service.getTasks(body = body)
        }
    }

    override suspend fun deleteTask(body: MutableMap<String, Any?>): ApiResult<ManageTaskResponse> {
        return apiService.getApiResult {
            service.deleteTask(body = body)
        }
    }

    override suspend fun addTask(body: MutableMap<String, Any?>): ApiResult<ManageTaskResponse> {
        return apiService.getApiResult {
            service.addTask(body = body)
        }
    }

}
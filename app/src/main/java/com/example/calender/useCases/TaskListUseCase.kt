package com.example.calender.useCases

import com.example.calender.common.ApiResult
import com.example.calender.data.Task
import com.example.calender.data.TaskListResponse
import com.example.calender.repo.TaskListRepository
import javax.inject.Inject

class TaskListUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository
) {
    suspend operator fun invoke(body: MutableMap<String, Any?>): ApiResult<TaskListResponse> {
        return taskListRepository.getTasks(body = body)
    }
}
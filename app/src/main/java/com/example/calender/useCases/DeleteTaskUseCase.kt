package com.example.calender.useCases

import com.example.calender.common.ApiResult
import com.example.calender.data.ManageTaskResponse
import com.example.calender.data.Task
import com.example.calender.data.TaskListResponse
import com.example.calender.repo.TaskListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository
){
    suspend operator fun invoke(body: MutableMap<String, Any?>): ApiResult<ManageTaskResponse> {
        return taskListRepository.deleteTask(body = body)
    }
}

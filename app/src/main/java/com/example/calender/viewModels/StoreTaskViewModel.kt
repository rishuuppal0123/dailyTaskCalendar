package com.example.calender.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calender.common.ApiResult
import com.example.calender.data.TaskModel
import com.example.calender.di.IoDispatcher
import com.example.calender.repo.TaskListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreTaskViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val taskListRepository: TaskListRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(StoreTaskUiState())
    val uiState: State<StoreTaskUiState> = _uiState

    fun editTaskTitle(title: String) {
        val isTitleValid = title.isNotBlank()
        _uiState.value = StoreTaskUiState(
            titleError = if (!isTitleValid) "Title can't be empty" else "",
            task = uiState.value.task.copy(title = title)
        )
    }

    fun editTaskDescription(desc: String) {
        _uiState.value = StoreTaskUiState(task = uiState.value.task.copy(description = desc))
    }

    fun addTask(userId: Int = 76681, createdDate: Long) {
        viewModelScope.launch(ioDispatcher) {
            val addTaskBody = mutableMapOf<String, Any?>()
            addTaskBody["user_id"] = userId
            addTaskBody["task"] = uiState.value.task.copy(created_date = createdDate)
            when (val result = taskListRepository.addTask(body = addTaskBody)) {
                is ApiResult.Error -> {
                    _uiState.value =
                        StoreTaskUiState(error = result.message ?: "Task can't be added")
                }

                is ApiResult.Success -> {
                    _uiState.value = StoreTaskUiState(success = true)
                }
            }
        }
    }

    data class StoreTaskUiState(
        val task: TaskModel = TaskModel(),
        val titleError: String = "",
        val isLoading: Boolean = false,
        val error: String = "",
        val success: Boolean = false
    )
}
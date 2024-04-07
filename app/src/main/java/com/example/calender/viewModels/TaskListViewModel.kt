package com.example.calender.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calender.common.ApiResult
//import com.example.calender.common.Resources
import com.example.calender.data.Task
import com.example.calender.di.IoDispatcher
import com.example.calender.useCases.DeleteTaskUseCase
import com.example.calender.useCases.TaskListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val taskListUseCase: TaskListUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = mutableStateOf(TaskListUiState())
    val uiState: State<TaskListUiState> = _uiState

    init {
        getTasks()
    }

    fun deleteTask(userId: Int = 76681, task_id: Int) {
        viewModelScope.launch(ioDispatcher) {
            val deleteTaskBody = mutableMapOf<String, Any?>()
            deleteTaskBody["user_id"] = userId
            deleteTaskBody["task_id"] = task_id
            when (val result = deleteTaskUseCase.invoke(body = deleteTaskBody)) {
                is ApiResult.Success -> {
                    getTasks()
                }

                is ApiResult.Error -> {
                    _uiState.value =
                        TaskListUiState(error = result.message ?: "Task cannot be deleted")
                }
            }
        }
    }

    fun getTasks(userId: Int = 76681) {
        _uiState.value = TaskListUiState(isLoading = true)
        viewModelScope.launch(ioDispatcher) {
            val getTaskListBody = mutableMapOf<String, Any?>()
            getTaskListBody["user_id"] = userId
            when (val result = taskListUseCase.invoke(body = getTaskListBody)) {
                is ApiResult.Error -> {
                    _uiState.value = TaskListUiState(
                        isLoading = false,
                        error = result.message ?: "Some error occurred"
                    )
                }

                is ApiResult.Success -> {
                    _uiState.value = TaskListUiState(
                        isLoading = false, tasks = result.data.tasks
                    )
                }
            }
        }
    }

    data class TaskListUiState(
        val tasks: List<Task> = emptyList(),
        val isLoading: Boolean = false,
        val error: String = ""
    )
}
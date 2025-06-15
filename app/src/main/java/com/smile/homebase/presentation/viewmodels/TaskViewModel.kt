package com.smile.homebase.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smile.homebase.domain.usecase.TaskUseCase
import com.smile.homebase.presentation.uiLayer.UserIntents
import com.smile.homebase.data.models.Task
import com.smile.homebase.data.repositoryImpl.TaskRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel: ViewModel() {

    private val taskUseCase = TaskUseCase(TaskRepositoryImpl())

    private val _taskState = MutableStateFlow<List<Task>>(listOf())
    val taskState: StateFlow<List<Task>> = _taskState.asStateFlow()
    private fun setTaskState(state: ArrayList<Task>) {
        _taskState.value = state
    }

    fun handleIntent(intent: UserIntents) {
        Log.d(TAG, "handleIntent")
        viewModelScope.launch(Dispatchers.IO) {
            when(intent) {
                UserIntents.TaskList -> {
                    Log.d(TAG, "handleIntent.TaskList")
                    setTaskState(ArrayList(taskUseCase.tasks))
                }
                is UserIntents.AddOneTask -> {
                    Log.d(TAG, "handleIntent.AddOneTask")
                    val taskList = taskUseCase.tasks
                    taskUseCase.addOneTask(intent.task)
                    Log.d(TAG, "handleIntent.AddOneTask.taskList.size = ${taskList.size}")
                    setTaskState(ArrayList(taskList))
                }
            }
        }
    }

    companion object {
        private const val TAG = "TaskViewModel"
    }
}
package com.smile.homebase.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.smile.homebase.domain.usecase.TaskUseCase
import com.smile.homebase.presentation.uiLayer.UserIntents
import com.smile.retrofitapp.domain.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel(private val taskUseCase: TaskUseCase): ViewModel() {
    private val _taskState = MutableStateFlow<List<Task>>(listOf())
    val taskState: StateFlow<List<Task>> = _taskState.asStateFlow()
    private fun setTaskState(state: ArrayList<Task>) {
        _taskState.value = ArrayList(state)
    }
    private val taskList: ArrayList<Task> = ArrayList()

    fun handleIntent(intent: UserIntents) {
        Log.d(TAG, "handleIntent.taskUseCase.addOneTask")
        if (intent is UserIntents.TaskWork) {
            taskUseCase.addOneTask(intent.task, taskList)
            Log.d(TAG, "handleIntent.taskUseCase.taskList.size = ${taskList.size}")
            setTaskState(taskList)
        }
    }

    companion object {
        private const val TAG = "TaskViewModel"
    }
}
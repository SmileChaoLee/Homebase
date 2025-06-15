package com.smile.homebase.domain.usecase

import com.smile.homebase.data.models.Task
import com.smile.homebase.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskUseCase(
    private val taskRepository: TaskRepository) {
    val tasks = ArrayList<Task>()
    init {
        CoroutineScope(Dispatchers.IO).launch {
            val taskList = taskRepository.getTasks()
            for (i in 0 until taskList.size) {
                tasks.add(taskList[i])
            }
        }
    }
    suspend fun addOneTask(task: Task) {
        tasks.add(task)
    }
}
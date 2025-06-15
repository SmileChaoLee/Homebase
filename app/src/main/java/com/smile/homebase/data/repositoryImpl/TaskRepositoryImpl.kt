package com.smile.homebase.data.repositoryImpl

import com.smile.homebase.data.models.Task
import com.smile.homebase.domain.repository.TaskRepository

class TaskRepositoryImpl: TaskRepository {
    override suspend fun getTasks(): ArrayList<Task> {
        val tasks = ArrayList<Task>()
        for (i in 0 until 10) {
            tasks.add(Task("LeeChao${i.toString().padStart(3, '0')}",
                "is done"))
        }
        return tasks
    }
}
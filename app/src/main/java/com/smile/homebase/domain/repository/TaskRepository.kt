package com.smile.homebase.domain.repository

import com.smile.homebase.data.models.Task

interface TaskRepository {
    suspend fun getTasks(): ArrayList<Task>
}
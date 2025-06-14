package com.smile.homebase.presentation.uiLayer

import com.smile.retrofitapp.domain.model.Task

sealed class UserIntents {
    data class TaskWork(val task: Task): UserIntents()
}
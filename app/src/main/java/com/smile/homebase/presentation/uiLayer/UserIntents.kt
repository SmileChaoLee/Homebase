package com.smile.homebase.presentation.uiLayer

import com.smile.homebase.data.models.Task

sealed class UserIntents {
    data object TaskList: UserIntents()
    data class AddOneTask(val task: Task): UserIntents()
}
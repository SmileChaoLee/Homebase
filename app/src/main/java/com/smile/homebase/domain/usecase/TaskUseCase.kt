package com.smile.homebase.domain.usecase

import com.smile.retrofitapp.domain.model.Task

class TaskUseCase {
    fun addOneTask(task: Task, list: ArrayList<Task>) {
        list.add(task)
    }
}
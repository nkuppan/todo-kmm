package com.nkuppan.todo

import android.app.Application
import com.nkuppan.todo.shared.DatabaseDriverFactory
import com.nkuppan.todo.shared.TaskRepository

class ToDoApplication: Application() {

    lateinit var repository: TaskRepository

    override fun onCreate() {
        super.onCreate()

        repository = TaskRepository(DatabaseDriverFactory(this))
    }
}
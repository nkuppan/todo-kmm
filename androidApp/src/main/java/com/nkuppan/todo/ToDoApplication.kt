package com.nkuppan.todo

import android.app.Application
import com.nkuppan.todo.shared.DatabaseDriverFactory
import com.nkuppan.todo.shared.TaskRepository
import com.nkuppan.todo.utils.SettingPrefManager

class ToDoApplication: Application() {

    lateinit var repository: TaskRepository

    override fun onCreate() {
        super.onCreate()

        SettingPrefManager.initialize(this)

        repository = TaskRepository(DatabaseDriverFactory(this))
    }
}
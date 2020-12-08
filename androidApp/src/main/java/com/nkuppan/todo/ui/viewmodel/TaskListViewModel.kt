package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.db.Task
import kotlinx.coroutines.launch

class TaskListViewModel(private val aApplication: Application) : AndroidViewModel(aApplication) {

    val taskList : MutableLiveData<List<Task>> = MutableLiveData()

    val noTaskFound : MutableLiveData<Boolean> = MutableLiveData()

    fun loadTaskList() {
        viewModelScope.launch {
            val allTaskList = (aApplication as ToDoApplication).repository.getAllTasks()
            this@TaskListViewModel.taskList.value = allTaskList
            this@TaskListViewModel.noTaskFound.value = allTaskList.isNullOrEmpty()
        }
    }
}
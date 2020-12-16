package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.db.TaskGroup
import kotlinx.coroutines.launch
import java.util.*

class TaskGroupListViewModel(private val aApplication: Application) :
    AndroidViewModel(aApplication) {

    private var _taskGroupList = MutableLiveData<List<TaskGroup>>()
    var taskGroupList : LiveData<List<TaskGroup>> = _taskGroupList

    fun loadTaskGroup() {
        viewModelScope.launch {
            val taskGroupList =
                (aApplication as ToDoApplication).repository.findAllGroups().toMutableList()

            this@TaskGroupListViewModel._taskGroupList.value = taskGroupList
        }
    }

    fun createNewListClick() {

    }

    fun openHelpAndFeedback() {

    }
}

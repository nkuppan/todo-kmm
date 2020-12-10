package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.db.TaskGroup
import com.nkuppan.todo.model.Task
import kotlinx.coroutines.launch
import java.util.*

class TaskListViewModel(private val aApplication: Application) : AndroidViewModel(aApplication) {

    val taskGroupList: MutableLiveData<List<String>> = MutableLiveData()

    val taskList: MutableLiveData<List<Task>> = MutableLiveData()

    val noTaskFound: MutableLiveData<Boolean> = MutableLiveData()

    private val taskGroupOriginalList = mutableListOf<TaskGroup>()

    private var selectedGroup: TaskGroup? = null

    fun loadTaskList() {
        viewModelScope.launch {
            val pendingTasks = (aApplication as ToDoApplication).repository.getPendingTask(
                selectedGroup?.id ?: "1"
            )
            val newTaskList = mutableListOf<Task>()
            repeat(pendingTasks.size) {
                newTaskList.add(pendingTasks[it].toNewTask())
            }
            this@TaskListViewModel.taskList.value = newTaskList
            this@TaskListViewModel.noTaskFound.value = pendingTasks.isNullOrEmpty()
        }
    }

    fun loadTaskGroup() {
        viewModelScope.launch {
            val taskGroupList =
                (aApplication as ToDoApplication).repository.findAllGroups().toMutableList()
            if (taskGroupList.isEmpty()) {
                taskGroupList.add(
                    TaskGroup(
                        "1",
                        "My List",
                        Date().time.toDouble(),
                        Date().time.toDouble()
                    )
                )

                if (selectedGroup == null) {
                    selectedGroup = taskGroupList[0]
                }
            }

            val groupNameList = mutableListOf<String>()

            repeat(taskGroupList.size) {
                groupNameList.add(taskGroupList[it].group_name)
            }

            this@TaskListViewModel.taskGroupList.value = groupNameList
        }
    }
}

private fun com.nkuppan.todo.db.Task.toNewTask(): Task {
    return Task(
        id,
        title,
        description,
        status,
        created_on,
        updated_on
    )
}

package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.todo.R
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.db.TaskGroup
import com.nkuppan.todo.model.Task
import kotlinx.coroutines.launch
import java.util.*

class TaskListViewModel(private val aApplication: Application) : AndroidViewModel(aApplication) {

    val taskGroupNameList: MutableLiveData<List<String>> = MutableLiveData()

    val taskList: MutableLiveData<List<Task>> = MutableLiveData()

    val completedList: MutableLiveData<List<Task>> = MutableLiveData()

    val pendingTaskFound: MutableLiveData<Boolean> = MutableLiveData()

    val completedTaskFound: MutableLiveData<Boolean> = MutableLiveData()

    val openCompletedTaskContainer: MutableLiveData<Boolean> = MutableLiveData()

    val completedCountText: MutableLiveData<String> = MutableLiveData()

    private var taskGroupList = mutableListOf<TaskGroup>()

    private var selectedGroup: TaskGroup? = null

    init {
        openCompletedTaskContainer.value = true
        pendingTaskFound.value = false
        completedTaskFound.value = false
    }

    fun loadTaskList() {
        viewModelScope.launch {
            loadPendingTask()
            loadCompletedTasks()
        }
    }

    private suspend fun loadPendingTask() {

        val pendingTasks = (aApplication as ToDoApplication).repository.getPendingTask(
            selectedGroup?.id ?: "1"
        )

        val newPendingTaskList = mutableListOf<Task>()
        if (pendingTasks.isNotEmpty()) {
            repeat(pendingTasks.size) {
                newPendingTaskList.add(pendingTasks[it].toNewTask())
            }
        }
        this@TaskListViewModel.taskList.value = newPendingTaskList
        this@TaskListViewModel.pendingTaskFound.value = pendingTasks.isNotEmpty()
    }

    private suspend fun loadCompletedTasks() {

        val completedTask = (aApplication as ToDoApplication).repository.getCompletedTask(
            selectedGroup?.id ?: "1"
        )

        val newCompletedTaskList = mutableListOf<Task>()
        if (completedTask.isNotEmpty()) {
            repeat(completedTask.size) {
                newCompletedTaskList.add(completedTask[it].toNewTask())
            }
        }
        this@TaskListViewModel.completedList.value = newCompletedTaskList
        this@TaskListViewModel.completedTaskFound.value = newCompletedTaskList.isNotEmpty()
        this@TaskListViewModel.completedCountText.value =
            aApplication.getString(R.string.completed_count, newCompletedTaskList.count())
    }

    fun loadTaskGroup() {
        viewModelScope.launch {
            taskGroupList =
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

            this@TaskListViewModel.taskGroupNameList.value = groupNameList
        }
    }

    fun saveThisTaskAsCompleted(aTask: Task) {
        viewModelScope.launch {

            aTask.status = 2
            aTask.updated_on = Date().time.toDouble()

            (aApplication as ToDoApplication).repository.insertTask(aTask.toDBTask())

            loadTaskList()
        }
    }

    fun completedTaskClick() {
        openCompletedTaskContainer.value = !openCompletedTaskContainer.value!!
    }
}

private fun com.nkuppan.todo.db.Task.toNewTask(): Task {
    return Task(
        id,
        group_id,
        title,
        description,
        status,
        created_on,
        updated_on
    )
}

private fun Task.toDBTask(): com.nkuppan.todo.db.Task {
    return com.nkuppan.todo.db.Task(
        id,
        group_id,
        title,
        description,
        status,
        created_on,
        updated_on
    )
}

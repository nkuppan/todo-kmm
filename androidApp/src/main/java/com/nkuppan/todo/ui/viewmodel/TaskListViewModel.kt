package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.todo.R
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.db.Task
import com.nkuppan.todo.db.TaskGroup
import com.nkuppan.todo.shared.CommonUtils
import com.nkuppan.todo.utils.SettingPrefManager
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

    val selectedTaskGroupName: MutableLiveData<String> = MutableLiveData()

    private var taskGroupList = mutableListOf<TaskGroup>()

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
            SettingPrefManager.getSelectedTaskGroup()
        )
        this@TaskListViewModel.taskList.value = pendingTasks
        this@TaskListViewModel.pendingTaskFound.value = pendingTasks.isNotEmpty()
    }

    private suspend fun loadCompletedTasks() {
        val completedTask = (aApplication as ToDoApplication).repository.getCompletedTask(
            SettingPrefManager.getSelectedTaskGroup()
        )
        this@TaskListViewModel.completedList.value = completedTask
        this@TaskListViewModel.completedTaskFound.value = completedTask.isNotEmpty()
        this@TaskListViewModel.completedCountText.value =
            aApplication.getString(R.string.completed_count, completedTask.count())
    }

    fun loadTaskGroup() {
        viewModelScope.launch {
            taskGroupList =
                (aApplication as ToDoApplication).repository.findAllGroups().toMutableList()

            if (SettingPrefManager.getSelectedTaskGroup().isBlank() && taskGroupList.isNotEmpty()) {
                SettingPrefManager.storeSelectedTaskGroup(taskGroupList[0].id)
            }

            val groupNameList = mutableListOf<String>()

            repeat(taskGroupList.size) {

                val taskGroup = taskGroupList[it]

                groupNameList.add(taskGroup.group_name)

                if (taskGroup.id == SettingPrefManager.getSelectedTaskGroup()) {
                    selectedTaskGroupName.value = taskGroup.group_name
                }
            }

            this@TaskListViewModel.taskGroupNameList.value = groupNameList
        }
    }

    fun saveThisTaskAsCompleted(aTask: Task) {
        viewModelScope.launch {

            val newTask = Task(
                aTask.id,
                aTask.group_id,
                aTask.title,
                aTask.description,
                status = 2,
                created_on = aTask.created_on,
                updated_on = CommonUtils.getDateTime().toDouble()
            )

            (aApplication as ToDoApplication).repository.insertTask(newTask)

            loadTaskList()
        }
    }

    fun completedTaskClick() {
        openCompletedTaskContainer.value = !openCompletedTaskContainer.value!!
    }
}
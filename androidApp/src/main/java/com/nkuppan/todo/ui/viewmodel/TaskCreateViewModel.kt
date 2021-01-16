package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.todo.R
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.db.Task
import com.nkuppan.todo.db.TaskGroup
import com.nkuppan.todo.extention.Event
import com.nkuppan.todo.shared.utils.CommonUtils
import com.nkuppan.todo.utils.SettingPrefManager
import kotlinx.coroutines.launch
import java.util.*

class TaskCreateViewModel(private val aApplication: Application) : AndroidViewModel(aApplication) {

    var title: MutableLiveData<String> = MutableLiveData()

    var description: MutableLiveData<String> = MutableLiveData()

    var showDescription: MutableLiveData<Boolean> = MutableLiveData()

    private val _success: MutableLiveData<Event<Unit>> = MutableLiveData()
    val success: LiveData<Event<Unit>> = _success

    private val _deleted: MutableLiveData<Event<Unit>> = MutableLiveData()
    val deleted: LiveData<Event<Unit>> = _deleted

    private val _updateMenu: MutableLiveData<Event<Unit>> = MutableLiveData()
    val updateMenu: LiveData<Event<Unit>> = _updateMenu

    private val _selectDateTime: MutableLiveData<Event<Unit>> = MutableLiveData()
    val selectDateTime: LiveData<Event<Unit>> = _selectDateTime

    private val _addSubTask: MutableLiveData<Event<Unit>> = MutableLiveData()
    val addSubTask: LiveData<Event<Unit>> = _addSubTask

    private val _snackBarText: MutableLiveData<Event<String>> = MutableLiveData()
    val snackBarMessage: LiveData<Event<String>> = _snackBarText

    private val _taskGroupList: MutableLiveData<Event<List<String>>> = MutableLiveData()
    val taskGroupList: LiveData<Event<List<String>>> = _taskGroupList

    private var _allTaskGroupList: MutableList<TaskGroup>? = null

    var taskEndDate: Long? = null

    private var taskValue: Task? = null

    fun loadTaskDetails(aTaskId: String?) {
        if (aTaskId.isNullOrEmpty()) {
            return
        }
        viewModelScope.launch {
            val task = (aApplication as ToDoApplication).repository.findThisTask(
                aTaskId
            )
            if (task != null) {
                taskValue = task
                title.value = task.title
                description.value = task.description
                taskEndDate = task.task_end_date?.toLong()
                _updateMenu.value = Event(Unit)
            }
        }
    }

    fun loadTaskGroupList() {

        viewModelScope.launch {

            val taskGroupList = (aApplication as ToDoApplication).repository.findAllGroups()

            if (taskGroupList.isNotEmpty()) {

                _allTaskGroupList = taskGroupList.toMutableList()

                val taskGroupNameList = mutableListOf<String>()

                taskGroupList.forEach {
                    taskGroupNameList.add(it.group_name)
                }

                _taskGroupList.value = Event(taskGroupNameList.toList())
            }
        }
    }

    fun createTaskClick() {

        val title = title.value
        val description = description.value

        if (title.isNullOrBlank()) {
            return
        }

        viewModelScope.launch {

            (aApplication as ToDoApplication).repository.insertTask(
                Task(
                    if (taskValue != null) taskValue!!.id else CommonUtils.getRandomUUID(),
                    group_id = SettingPrefManager.getSelectedTaskGroup(),
                    title = title,
                    description = description,
                    task_end_date = taskEndDate?.toDouble(),
                    status = 1,
                    created_on = if (taskValue != null) taskValue!!.created_on else Date().time.toDouble(),
                    updated_on = Date().time.toDouble()
                )
            )

            _success.value = Event(Unit)
            _snackBarText.value = Event(aApplication.getString(R.string.successfully_added))
        }
    }

    fun selectDateTimeClick() {
        _selectDateTime.value = Event(Unit)
    }

    fun addDescriptionClick() {
        showDescription.value = true
    }

    fun addSubTaskClick() {
        _addSubTask.value = Event(Unit)
    }

    fun updateDateTime(aDate: Long) {
        taskEndDate = aDate
    }

    fun markThisTaskAsCompleted() {
        viewModelScope.launch {
            taskValue?.let {
                (aApplication as ToDoApplication).repository.markThisTaskAsCompleted(it)
                _success.value = Event(Unit)
            }
        }
    }

    fun revertThisTask() {
        viewModelScope.launch {
            taskValue?.let {
                (aApplication as ToDoApplication).repository.revertThisTask(it)
                _success.value = Event(Unit)
            }
        }
    }

    fun isCompleted(): Boolean {
        return if (taskValue != null) {
            taskValue!!.status == 2L
        } else {
            false
        }
    }

    fun deleteThisTask() {
        viewModelScope.launch {
            taskValue?.let {
                (aApplication as ToDoApplication).repository.removeTask(it)
                _success.value = Event(Unit)
            }
        }
    }
}
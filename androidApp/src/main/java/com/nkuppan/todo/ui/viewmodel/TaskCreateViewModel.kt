package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.todo.R
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.db.SubTask
import com.nkuppan.todo.db.Task
import com.nkuppan.todo.db.TaskGroup
import com.nkuppan.todo.extention.Event
import com.nkuppan.todo.shared.utils.CommonUtils
import com.nkuppan.todo.utils.AppUIUtils
import com.nkuppan.todo.utils.SettingPrefManager
import kotlinx.coroutines.launch
import java.util.*

class TaskCreateViewModel(private val aApplication: Application) : AndroidViewModel(aApplication) {

    var title: MutableLiveData<String> = MutableLiveData()

    var description: MutableLiveData<String> = MutableLiveData()

    var showDescription: MutableLiveData<Boolean> = MutableLiveData()

    private val _changeTaskGroup: MutableLiveData<Event<Unit>> = MutableLiveData()
    val changeTaskGroup: LiveData<Event<Unit>> = _changeTaskGroup

    private val _success: MutableLiveData<Event<Unit>> = MutableLiveData()
    val success: LiveData<Event<Unit>> = _success

    private val _silentSuccess: MutableLiveData<Event<Unit>> = MutableLiveData()
    val silentSuccess: LiveData<Event<Unit>> = _silentSuccess

    private val _deleted: MutableLiveData<Event<Unit>> = MutableLiveData()
    val deleted: LiveData<Event<Unit>> = _deleted

    private val _updateMenu: MutableLiveData<Event<Unit>> = MutableLiveData()
    val updateMenu: LiveData<Event<Unit>> = _updateMenu

    private val _selectDateTime: MutableLiveData<Event<Unit>> = MutableLiveData()
    val selectDateTime: LiveData<Event<Unit>> = _selectDateTime

    private val _snackBarText: MutableLiveData<Event<String>> = MutableLiveData()
    val snackBarMessage: LiveData<Event<String>> = _snackBarText

    private val _taskGroupName: MutableLiveData<Event<String>> = MutableLiveData()
    val taskGroupName: LiveData<Event<String>> = _taskGroupName

    private var allTaskGroupList: MutableList<TaskGroup>? = null

    var subTaskList: MutableLiveData<List<SubTask>?> = MutableLiveData()

    val taskEndDateString: MutableLiveData<String> = MutableLiveData()

    var taskEndDate: Long? = null

    var taskGroupId: String? = null

    init {
        taskEndDateString.value = aApplication.getString(R.string.add_date_time)
    }

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
                taskGroupId = task.group_id
                _updateMenu.value = Event(Unit)

                updateTaskEndDate()

                val subTaskList = aApplication.repository.getSubTaskList(
                    aTaskId
                )

                this@TaskCreateViewModel.subTaskList.value = subTaskList
            }
        }
    }

    private fun updateTaskEndDate() {
        if (taskEndDate != null && taskEndDate!! > 0) {
            taskEndDateString.value = AppUIUtils.getTaskEndDate(taskEndDate?.toDouble())
        }
    }

    fun loadTaskGroupList() {

        viewModelScope.launch {

            val taskGroupList = (aApplication as ToDoApplication).repository.findAllGroups()

            if (taskGroupList.isNotEmpty()) {

                allTaskGroupList = taskGroupList.toMutableList()

                var groupName: String = aApplication.getString(R.string.my_list)

                taskGroupList.forEach {
                    if (it.id == SettingPrefManager.getSelectedTaskGroup()) {
                        groupName = it.group_name
                    }
                }

                _taskGroupName.value = Event(groupName)
            }
        }
    }

    fun saveTaskDetails(saveSilently: Boolean = false) {

        if (saveSilently && taskValue == null) {
            return
        }

        val title = title.value
        val description = description.value

        if (title.isNullOrBlank()) {
            return
        }

        viewModelScope.launch {

            val newConstructedTask = Task(
                if (taskValue != null) taskValue!!.id else CommonUtils.getRandomUUID(),
                group_id = if (taskGroupId != null)
                    taskGroupId!!
                else
                    SettingPrefManager.getSelectedTaskGroup(),
                title = title,
                description = description,
                task_end_date = taskEndDate?.toDouble(),
                status = if (taskValue != null) taskValue!!.status else 1,
                created_on = if (taskValue != null)
                    taskValue!!.created_on
                else
                    CommonUtils.getDateTime().toDouble(),
                updated_on = CommonUtils.getDateTime().toDouble()
            )

            (aApplication as ToDoApplication).repository.insertTask(newConstructedTask)

            if (!saveSilently) {
                _success.value = Event(Unit)
                _snackBarText.value = Event(aApplication.getString(R.string.successfully_added))
            } else {
                _silentSuccess.value = Event(Unit)
            }

            if (saveSilently) {
                taskValue = newConstructedTask
            }
        }
    }

    fun selectDateTimeClick() {
        _selectDateTime.value = Event(Unit)
    }

    fun addDescriptionClick() {
        showDescription.value = true
    }

    fun updateDateTime(aDate: Long) {
        taskEndDate = aDate
        updateTaskEndDate()
        saveTaskDetails(saveSilently = true)
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

    fun changeTaskGroup() {
        _changeTaskGroup.value = Event(Unit)
    }

    fun updateTaskGroup(aTaskGroup: TaskGroup) {
        viewModelScope.launch {
            taskValue?.let { aTask ->
                (aApplication as ToDoApplication).repository.updateTaskGroup(aTask, aTaskGroup)
                taskGroupId = aTaskGroup.id
                _taskGroupName.value = Event(aTaskGroup.group_name)
                _silentSuccess.value = Event(Unit)
                saveTaskDetails(saveSilently = true)
            }
        }
    }

    fun getSelectedTaskGroupId(): String {
        return taskValue!!.group_id
    }

    fun getTaskId(): String {
        return taskValue!!.id
    }
}
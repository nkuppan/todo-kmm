package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.todo.R
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.db.TaskGroup
import com.nkuppan.todo.extention.Event
import com.nkuppan.todo.shared.utils.CommonUtils
import com.nkuppan.todo.utils.SettingPrefManager
import kotlinx.coroutines.launch
import java.util.*

class TaskGroupCreateViewModel(private val aApplication: Application) :
    AndroidViewModel(aApplication) {

    private val _taskGroupName: MutableLiveData<String?> = MutableLiveData()
    val taskGroupName: MutableLiveData<String?> = _taskGroupName

    private val _success: MutableLiveData<Event<Unit>> = MutableLiveData()
    val success: LiveData<Event<Unit>> = _success

    private val _snackBarText: MutableLiveData<Event<String>> = MutableLiveData()
    val snackBarMessage: LiveData<Event<String>> = _snackBarText

    private var taskGroup: TaskGroup? = null

    fun updateTaskGroupValue(aTaskGroupId: String?) {

        if (aTaskGroupId.isNullOrBlank()) {
            return
        }

        viewModelScope.launch {

            taskGroup = (aApplication as ToDoApplication).repository.findGroupById(aTaskGroupId)

            if (taskGroup?.group_name.isNullOrEmpty()) {
                return@launch
            }

            _taskGroupName.value = taskGroup?.group_name
        }
    }

    fun createOrUpdateTaskGroup() {

        if (taskGroupName.value.isNullOrBlank()) {
            return
        }

        viewModelScope.launch {

            val taskGroup = if (taskGroup != null) {
                TaskGroup(
                    taskGroup!!.id,
                    taskGroupName.value.toString(),
                    taskGroup!!.created_on,
                    CommonUtils.getDateTime().toDouble()
                )
            } else {
                TaskGroup(
                    CommonUtils.getRandomUUID(),
                    taskGroupName.value.toString(),
                    CommonUtils.getDateTime().toDouble(),
                    CommonUtils.getDateTime().toDouble()
                )
            }

            (aApplication as ToDoApplication).repository.insertTaskGroup(taskGroup)

            SettingPrefManager.storeSelectedTaskGroup(taskGroup.id)

            _success.value = Event(Unit)
            _snackBarText.value = Event(aApplication.getString(R.string.successfully_added))
        }
    }
}
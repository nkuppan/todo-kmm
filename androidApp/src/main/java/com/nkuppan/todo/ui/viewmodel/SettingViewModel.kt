package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.extention.Event
import com.nkuppan.todo.shared.utils.Constants
import com.nkuppan.todo.utils.SettingPrefManager
import kotlinx.coroutines.launch

class SettingViewModel(private val aApplication: Application) :
    AndroidViewModel(aApplication) {

    private val _renameList: MutableLiveData<Event<Unit>> = MutableLiveData()
    val renameList: LiveData<Event<Unit>> = _renameList

    private val _deleteCompletedTaskClick: MutableLiveData<Event<Unit>> = MutableLiveData()
    val allRemovedClick: LiveData<Event<Unit>> = _deleteCompletedTaskClick

    private val _deleteCompletedTask: MutableLiveData<Event<Unit>> = MutableLiveData()
    val deleteCompletedTask: LiveData<Event<Unit>> = _deleteCompletedTask

    private val _taskGroupDeletedClick: MutableLiveData<Event<Long>> = MutableLiveData()
    val taskGroupDeletedClick: LiveData<Event<Long>> = _taskGroupDeletedClick

    private val _taskGroupDeleted: MutableLiveData<Event<Unit>> = MutableLiveData()
    val taskGroupDeleted: LiveData<Event<Unit>> = _taskGroupDeleted

    private val _openSortOption: MutableLiveData<Event<Unit>> = MutableLiveData()
    val openSortOption: LiveData<Event<Unit>> = _openSortOption

    private val _themeSelection: MutableLiveData<Event<Unit>> = MutableLiveData()
    val themeSelection: LiveData<Event<Unit>> = _themeSelection

    val isDefaultList: MutableLiveData<Boolean> = MutableLiveData()

    val isCompletedAvailable: MutableLiveData<Boolean> = MutableLiveData()

    var completedTaskCount: Int = 0

    init {
        isCompletedAvailable.value = false
        isDefaultList.value = SettingPrefManager.getSelectedTaskGroup() == "1"
        loadTask()
    }

    private fun loadTask() {
        viewModelScope.launch {
            val tasks = (aApplication as ToDoApplication).repository.getCompletedTask(
                SettingPrefManager.getSelectedTaskGroup()
            )

            completedTaskCount = tasks.size
            isCompletedAvailable.value = completedTaskCount > 0
        }
    }

    fun onRenameListClick() {
        _renameList.value = Event(Unit)
    }

    fun openSortOptionMenu() {
        _openSortOption.value = Event(Unit)
    }

    fun deleteAllCompletedTaskClick() {
        if (isCompletedAvailable.value == true) {
            return
        }
        _deleteCompletedTaskClick.value = Event(Unit)
    }

    fun deleteAllCompletedTask() {

        if (isCompletedAvailable.value == true) {
            return
        }

        viewModelScope.launch {
            (aApplication as ToDoApplication).repository.removeCompletedTask(
                SettingPrefManager.getSelectedTaskGroup()
            )
            _deleteCompletedTask.value = Event(Unit)
        }
    }

    fun deleteThisTaskGroupClick() {

        if (isDefaultList.value == true) {
            return
        }

        viewModelScope.launch {
            if (aApplication is ToDoApplication) {
                val taskCount = aApplication.repository.getAllTasks(
                    SettingPrefManager.getSelectedTaskGroup()
                )
                if (taskCount.isNotEmpty()) {
                    _taskGroupDeletedClick.value = Event(taskCount.size.toLong())
                } else {
                    deleteThisTaskGroup()
                }
            }
        }
    }

    fun deleteThisTaskGroup() {

        if (isDefaultList.value == true) {
            return
        }

        viewModelScope.launch {
            if (aApplication is ToDoApplication) {
                aApplication.repository.removeAllTasks(
                    SettingPrefManager.getSelectedTaskGroup()
                )
                aApplication.repository.removeThisGroup(
                    SettingPrefManager.getSelectedTaskGroup()
                )
                SettingPrefManager.storeSelectedTaskGroup(Constants.DEFAULT_LIST_ID)
                _taskGroupDeleted.value = Event(Unit)
            }
        }
    }

    fun onThemeSelectClick() {
        _themeSelection.value = Event(Unit)
    }
}
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

    private val _allRemoved: MutableLiveData<Event<Unit>> = MutableLiveData()
    val allRemoved: LiveData<Event<Unit>> = _allRemoved

    private val _taskGroupRemoved: MutableLiveData<Event<Unit>> = MutableLiveData()
    val taskGroupRemoved: LiveData<Event<Unit>> = _taskGroupRemoved

    private val _openSortOption: MutableLiveData<Event<Unit>> = MutableLiveData()
    val openSortOption: LiveData<Event<Unit>> = _openSortOption

    private val _themeSelection: MutableLiveData<Event<Unit>> = MutableLiveData()
    val themeSelection: LiveData<Event<Unit>> = _themeSelection

    val isDefaultList: MutableLiveData<Boolean> = MutableLiveData()

    val isCompletedAvailable: MutableLiveData<Boolean> = MutableLiveData()

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

            isCompletedAvailable.value = tasks.isNotEmpty() == true
        }
    }

    fun onRenameListClick() {
        _renameList.value = Event(Unit)
    }

    fun openSortOptionMenu() {
        _openSortOption.value = Event(Unit)
    }

    fun deleteAllCompletedTask() {

        if (isCompletedAvailable.value == true) {
            return
        }

        viewModelScope.launch {
            (aApplication as ToDoApplication).repository.removeCompletedTask(
                SettingPrefManager.getSelectedTaskGroup()
            )
            _allRemoved.value = Event(Unit)
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
                _taskGroupRemoved.value = Event(Unit)
            }
        }
    }

    fun onThemeSelectClick() {
        _themeSelection.value = Event(Unit)
    }
}
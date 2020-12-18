package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ancient.essentials.extentions.Event
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.utils.SettingPrefManager
import kotlinx.coroutines.launch

class SettingViewModel(private val aApplication: Application) :
    AndroidViewModel(aApplication) {

    private val _renameList: MutableLiveData<Event<Unit>> = MutableLiveData()
    val renameList: LiveData<Event<Unit>> = _renameList

    fun onRenameListClick() {
        _renameList.value = Event(Unit)
    }

    fun deleteAllCompletedTask() {
        viewModelScope.launch {
            (aApplication as ToDoApplication).repository.removeCompletedTask(
                SettingPrefManager.getSelectedTaskGroup()
            )
        }
    }

    fun deleteThisTaskGroup() {
        viewModelScope.launch {
            if (aApplication is ToDoApplication) {
                aApplication.repository.removeAllTasks(
                    SettingPrefManager.getSelectedTaskGroup()
                )
                aApplication.repository.removeThisGroup(
                    SettingPrefManager.getSelectedTaskGroup()
                )
            }
        }
    }
}
package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ancient.essentials.extentions.Event
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.db.TaskGroup
import kotlinx.coroutines.launch
import java.util.*

class TaskGroupListViewModel(private val aApplication: Application) :
    AndroidViewModel(aApplication) {

    private var _taskGroupList = MutableLiveData<List<TaskGroup>>()
    var taskGroupList : LiveData<List<TaskGroup>> = _taskGroupList

    private var _createNewList = MutableLiveData<Event<Unit>>()
    var createNewList : LiveData<Event<Unit>> = _createNewList

    private var _openHelpAndFeedback = MutableLiveData<Event<Unit>>()
    var openHelpAndFeedback : LiveData<Event<Unit>> = _openHelpAndFeedback

    private val _openOpenSourceLicenses = MutableLiveData<Event<Unit>>()
    val openOpenSourceLicenses : LiveData<Event<Unit>> = _openOpenSourceLicenses

    fun loadTaskGroup() {
        viewModelScope.launch {
            val taskGroupList =
                (aApplication as ToDoApplication).repository.findAllGroups().toMutableList()

            this@TaskGroupListViewModel._taskGroupList.value = taskGroupList
        }
    }

    fun createNewListClick() {
        _createNewList.value = Event(Unit)
    }

    fun openHelpAndFeedback() {
        _openHelpAndFeedback.value = Event(Unit)
    }

    fun openOpenSourceLicenses() {
        _openOpenSourceLicenses.value = Event(Unit)
    }
}

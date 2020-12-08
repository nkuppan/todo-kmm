package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ancient.essentials.extentions.Event
import com.nkuppan.todo.ToDoApplication
import com.nkuppan.todo.db.Task
import com.nkuppan.todo.shared.CommonUtils
import kotlinx.coroutines.launch
import java.util.*

class TaskCreateViewModel(private val aApplication: Application) : AndroidViewModel(aApplication) {

    var title: MutableLiveData<String> = MutableLiveData()

    var description: MutableLiveData<String> = MutableLiveData()

    private val _success: MutableLiveData<Event<Unit>> = MutableLiveData()
    val success: LiveData<Event<Unit>> = _success

    fun createTaskClick() {
        viewModelScope.launch {
            (aApplication as ToDoApplication).repository.insertTask(
                Task(
                    CommonUtils.getRandomUUID(),
                    title = title.toString(),
                    description = description.toString(),
                    status = 1,
                    created_on = Date().time.toDouble(),
                    updated_on = Date().time.toDouble()
                )
            )

            _success.value = Event(Unit)
        }
    }

    fun selectDateTimeClick() {

    }

    fun addDescriptionClick() {

    }
}
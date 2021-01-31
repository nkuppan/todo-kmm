package com.nkuppan.todo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SubTaskViewModel(private val aApplication: Application) :
    AndroidViewModel(aApplication) {

    val description: MutableLiveData<String> = MutableLiveData()
}
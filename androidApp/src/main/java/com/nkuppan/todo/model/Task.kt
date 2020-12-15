package com.nkuppan.todo.model

data class Task(
    val id: String,
    val group_id: String,
    val title: String,
    val description: String?,
    var status: Long,
    val created_on: Double,
    var updated_on: Double,
)
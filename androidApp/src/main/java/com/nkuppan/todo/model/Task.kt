package com.nkuppan.todo.model

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val status: Long,
    val created_on: Double,
    val updated_on: Double,
)
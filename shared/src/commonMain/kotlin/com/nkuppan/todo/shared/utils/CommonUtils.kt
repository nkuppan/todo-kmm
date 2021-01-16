package com.nkuppan.todo.shared.utils

expect object CommonUtils {
    fun getRandomUUID(): String
    fun getDateTime(): Long
    fun getDeviceId(): String
}
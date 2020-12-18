package com.nkuppan.todo.shared

expect object CommonUtils {
    fun getRandomUUID(): String
    fun getDateTime(): Long
    fun getDeviceId(): String
}
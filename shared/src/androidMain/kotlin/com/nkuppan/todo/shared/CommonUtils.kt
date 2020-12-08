package com.nkuppan.todo.shared

import java.util.*

actual object CommonUtils {
    actual fun getRandomUUID(): String {
        return UUID.randomUUID().toString()
    }
}
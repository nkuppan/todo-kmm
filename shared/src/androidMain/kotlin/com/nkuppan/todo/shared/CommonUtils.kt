package com.nkuppan.todo.shared

import java.util.*

actual class CommonUtils {
    actual fun getRandomUUID(): String {
        return UUID.randomUUID().toString()
    }
}
package com.nkuppan.todo.shared

import android.os.Build
import java.util.*

actual object CommonUtils {

    actual fun getRandomUUID(): String {
        return UUID.randomUUID().toString()
    }

    actual fun getDateTime(): Long {
        return Date().time
    }

    actual fun getDeviceId(): String {
        return Build.DEVICE
    }
}
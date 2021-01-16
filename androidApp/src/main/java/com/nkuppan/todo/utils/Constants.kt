package com.nkuppan.todo.utils

import android.os.Build
import com.nkuppan.todo.shared.utils.CommonUtils

object UiUtils {
    fun getEmailContent(): String {
        return " Android Device : ${CommonUtils.getDeviceId()}\n" +
                " OS Version Name : ${Build.VERSION.SDK_INT}\n" +
                " Device Name : ${Build.MODEL} \n"
    }
}

object RequestCode {
    const val REQUEST_CODE_TASK_GROUP_CREATE = 1000
}

object RequestParam {
    const val DATE_TIME: String = "date_time"
    const val TASK_GROUP_ID = "task_group_id"
    const val TASK_ID = "task_id"
}
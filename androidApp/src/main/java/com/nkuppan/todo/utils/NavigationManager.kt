package com.nkuppan.todo.utils

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.nkuppan.todo.ui.activity.TaskGroupCreateActivity

object NavigationManager {

    fun openTaskGroupPage(
        aFragment: Fragment,
        aTaskGroupId: String?,
        aResultLauncher: ActivityResultLauncher<Intent>
    ) {
        val intent = Intent(
            aFragment.requireContext(),
            TaskGroupCreateActivity::class.java
        )

        intent.putExtras(
            Bundle().apply {
                putString(RequestParam.TASK_GROUP_ID, aTaskGroupId)
            }
        )

        aResultLauncher.launch(intent)
    }
}
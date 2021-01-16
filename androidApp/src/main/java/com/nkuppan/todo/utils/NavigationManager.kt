package com.nkuppan.todo.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nkuppan.todo.R
import com.nkuppan.todo.ui.activity.TaskEditActivity
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

    fun openTaskEditPage(
        aFragment: Fragment,
        aTaskId: String?,
        aResultLauncher: ActivityResultLauncher<Intent>
    ) {
        val intent = Intent(
            aFragment.requireContext(),
            TaskEditActivity::class.java
        )

        intent.putExtras(
            Bundle().apply {
                putString(RequestParam.TASK_ID, aTaskId)
            }
        )

        aResultLauncher.launch(intent)
    }

    fun Fragment.relaunchMainScreen() {
        this.findNavController().setGraph(R.navigation.overall_navigation, null)
    }

    fun Fragment.setSuccessResult(aReturnValue: Intent? = null) {
        setSuccessResult(this, Activity.RESULT_OK, aReturnValue)
    }

    fun Fragment.setFailureResult() {
        setSuccessResult(this, Activity.RESULT_CANCELED)
    }

    private fun setSuccessResult(
        aFragment: Fragment,
        aResultCode: Int,
        aReturnValue: Intent? = null
    ) {
        aFragment.requireActivity().setResult(aResultCode, aReturnValue)
        aFragment.requireActivity().finish()
    }
}
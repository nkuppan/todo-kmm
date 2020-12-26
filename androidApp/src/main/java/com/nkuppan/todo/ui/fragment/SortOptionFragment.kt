package com.nkuppan.todo.ui.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nkuppan.todo.R
import com.nkuppan.todo.utils.SettingPrefManager

class SortOptionFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
        dialogBuilder.setTitle(getString(R.string.sort_by))

        dialogBuilder.setSingleChoiceItems(
            R.array.sort_options,
            SettingPrefManager.getFilterType()
        ) { _, aFilterOption ->
            SettingPrefManager.storeFilterOption(aFilterOption)
            findNavController().setGraph(R.navigation.overall_navigation, null)
            dismiss()
        }

        return dialogBuilder.create()
    }
}
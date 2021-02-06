package com.nkuppan.todo.ui.fragment

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentSettingsBinding
import com.nkuppan.todo.extention.EventObserver
import com.nkuppan.todo.extention.autoCleared
import com.nkuppan.todo.ui.viewmodel.SettingViewModel
import com.nkuppan.todo.utils.AppUIUtils
import com.nkuppan.todo.utils.NavigationManager
import com.nkuppan.todo.utils.NavigationManager.relaunchMainScreen
import com.nkuppan.todo.utils.SettingPrefManager
import java.util.*

class SettingFragment : BottomSheetDialogFragment() {

    private var dataBinding: FragmentSettingsBinding by autoCleared()

    private var viewModel: SettingViewModel by autoCleared()

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                relaunchMainScreen()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        dataBinding = FragmentSettingsBinding.bind(view)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.renameList.observe(this.viewLifecycleOwner, {
            NavigationManager.openTaskGroupPage(
                this,
                SettingPrefManager.getSelectedTaskGroup(),
                resultLauncher
            )
        })

        viewModel.allRemovedClick.observe(viewLifecycleOwner, EventObserver {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.delete_all_completed_tasks)
                .setMessage(
                    String.format(
                        Locale.getDefault(),
                        getString(R.string.delete_message),
                        viewModel.completedTaskCount
                    )
                )
                .setPositiveButton(R.string.text_delete) { _: DialogInterface, _: Int ->
                    viewModel.deleteAllCompletedTask()
                }
                .setNegativeButton(R.string.text_cancel) { _: DialogInterface, _: Int ->
                    //Nothing to do
                }.create().show()
        })

        viewModel.deleteCompletedTask.observe(viewLifecycleOwner, EventObserver {
            relaunchMainScreen()
        })

        viewModel.taskGroupDeletedClick.observe(viewLifecycleOwner, EventObserver {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.delete_this_list)
                .setMessage(
                    String.format(
                        Locale.getDefault(),
                        getString(R.string.delete_list_message),
                        it
                    )
                )
                .setPositiveButton(R.string.text_delete) { _: DialogInterface, _: Int ->
                    viewModel.deleteThisTaskGroup()
                }
                .setNegativeButton(R.string.text_cancel) { _: DialogInterface, _: Int ->
                    //Nothing to do
                }.create().show()
        })

        viewModel.taskGroupDeleted.observe(viewLifecycleOwner, EventObserver {
            relaunchMainScreen()
        })

        viewModel.themeSelection.observe(viewLifecycleOwner, EventObserver {
            AppUIUtils.showThemeSelection(requireContext()) {
                relaunchMainScreen()
            }
        })

        viewModel.openSortOption.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(
                SettingFragmentDirections.actionSettingFragmentToSortOptionFragment()
            )
        })
    }
}
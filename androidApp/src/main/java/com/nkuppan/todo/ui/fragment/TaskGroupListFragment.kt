package com.nkuppan.todo.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentGroupListBinding
import com.nkuppan.todo.db.TaskGroup
import com.nkuppan.todo.extention.EventObserver
import com.nkuppan.todo.extention.autoCleared
import com.nkuppan.todo.shared.utils.Constants
import com.nkuppan.todo.ui.adapter.TaskGroupListAdapter
import com.nkuppan.todo.ui.viewmodel.TaskGroupListViewModel
import com.nkuppan.todo.utils.NavigationManager
import com.nkuppan.todo.utils.SettingPrefManager
import com.nkuppan.todo.utils.ShareUtils
import com.nkuppan.todo.utils.UiUtils


class TaskGroupListFragment : BottomSheetDialogFragment() {

    private var dataBinding: FragmentGroupListBinding by autoCleared()

    private var viewModel: TaskGroupListViewModel by autoCleared()

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                relaunchMainScreen()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_list, container, false)
        viewModel = ViewModelProvider(this).get(TaskGroupListViewModel::class.java)
        dataBinding = FragmentGroupListBinding.bind(view)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = TaskGroupListAdapter { aTaskGroup: TaskGroup, _: Int ->
            SettingPrefManager.storeSelectedTaskGroup(aTaskGroupId = aTaskGroup.id)
            relaunchMainScreen()
        }

        dataBinding.groupList.adapter = adapter

        viewModel.taskGroupList.observe(this.viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.openHelpAndFeedback.observe(this.viewLifecycleOwner, EventObserver {
            ShareUtils.sendEmailFeedback(
                requireContext(),
                Constants.EMAIL_ACCOUNT,
                Constants.EMAIL_SUBJECT,
                UiUtils.getEmailContent()
            )
        })

        viewModel.openOpenSourceLicenses.observe(this.viewLifecycleOwner, EventObserver {
            startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))
        })

        viewModel.createNewList.observe(this.viewLifecycleOwner, EventObserver {
            NavigationManager.openTaskGroupPage(this, null, resultLauncher)
        })

        viewModel.loadTaskGroup()
    }

    private fun relaunchMainScreen() {
        findNavController().setGraph(R.navigation.overall_navigation, null)
        dismiss()
    }
}
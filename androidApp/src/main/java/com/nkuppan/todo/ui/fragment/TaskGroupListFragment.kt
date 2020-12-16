package com.nkuppan.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ancient.essentials.extentions.autoCleared
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentGroupListBinding
import com.nkuppan.todo.db.TaskGroup
import com.nkuppan.todo.ui.adapter.TaskGroupListAdapter
import com.nkuppan.todo.ui.viewmodel.TaskGroupListViewModel
import com.nkuppan.todo.utils.SettingPrefManager

class TaskGroupListFragment : BottomSheetDialogFragment() {

    private var dataBinding: FragmentGroupListBinding by autoCleared()

    private var viewModel: TaskGroupListViewModel by autoCleared()

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

        val adapter = TaskGroupListAdapter { aTaskGroup: TaskGroup, aType: Int ->
            SettingPrefManager.storeSelectedTaskGroup(aTaskGroupId = aTaskGroup.id)
            findNavController().setGraph(R.navigation.overall_navigation, null)
            dismiss()
        }

        dataBinding.groupList.adapter = adapter

        viewModel.taskGroupList.observe(this.viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.loadTaskGroup()
    }
}
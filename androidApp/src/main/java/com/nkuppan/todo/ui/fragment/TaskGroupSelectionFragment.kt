package com.nkuppan.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentSelectTaskGroupBinding
import com.nkuppan.todo.db.TaskGroup
import com.nkuppan.todo.extention.autoCleared
import com.nkuppan.todo.ui.adapter.TaskGroupSelectionListAdapter
import com.nkuppan.todo.ui.viewmodel.TaskGroupListViewModel
import com.nkuppan.todo.utils.RequestParam

class TaskGroupSelectionFragment : BottomSheetDialogFragment() {

    private var dataBinding: FragmentSelectTaskGroupBinding by autoCleared()

    private var viewModel: TaskGroupListViewModel by autoCleared()

    var selection: ((TaskGroup) -> Unit)? = null

    var selectedTaskGroupId: String? = null

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        selectedTaskGroupId = args?.getString(RequestParam.TASK_GROUP_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_task_group, container, false)
        viewModel = ViewModelProvider(this).get(TaskGroupListViewModel::class.java)
        dataBinding = FragmentSelectTaskGroupBinding.bind(view)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            TaskGroupSelectionListAdapter(selectedTaskGroupId) { aTaskGroup: TaskGroup, _: Int ->
                selection?.invoke(aTaskGroup)
                dismiss()
            }
        dataBinding.taskGroupList.adapter = adapter

        viewModel.taskGroupList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.loadTaskGroup()
    }
}
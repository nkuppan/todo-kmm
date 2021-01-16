package com.nkuppan.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTaskCreateBinding
import com.nkuppan.todo.extention.EventObserver
import com.nkuppan.todo.extention.autoCleared
import com.nkuppan.todo.ui.viewmodel.TaskCreateViewModel
import com.nkuppan.todo.utils.AppUIUtils
import com.nkuppan.todo.utils.NavigationManager.relaunchMainScreen

class TaskCreateFragment : BottomSheetDialogFragment() {

    private var viewModel: TaskCreateViewModel by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_create, container, false)
        viewModel = ViewModelProvider(this).get(TaskCreateViewModel::class.java)
        val binding = FragmentTaskCreateBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectDateTime.observe(viewLifecycleOwner, EventObserver {
            AppUIUtils.showDatePickerDialog(this, viewModel.taskEndDate) {
                viewModel.updateDateTime(it)
            }
        })

        viewModel.success.observe(viewLifecycleOwner, EventObserver {
            relaunchMainScreen()
        })
    }
}
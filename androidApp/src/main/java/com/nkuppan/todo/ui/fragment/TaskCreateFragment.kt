package com.nkuppan.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ancient.essentials.extentions.EventObserver
import com.ancient.essentials.extentions.autoCleared
import com.ancient.essentials.extentions.setupStringSnackbar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTaskCreateBinding
import com.nkuppan.todo.ui.viewmodel.TaskCreateViewModel

class TaskCreateFragment : BottomSheetDialogFragment() {

    private var dataBinding: FragmentTaskCreateBinding by autoCleared()

    private var viewModel: TaskCreateViewModel by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_create, container, false)
        viewModel = ViewModelProvider(this).get(TaskCreateViewModel::class.java)
        dataBinding = FragmentTaskCreateBinding.bind(view)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.root.setupStringSnackbar(
            this,
            viewModel.snackBarMessage,
            Snackbar.LENGTH_SHORT
        )

        viewModel.selectDateTime.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(R.id.action_taskCreateDialogFragment_to_dateTimePickerFragment)
        })

        viewModel.success.observe(viewLifecycleOwner, EventObserver {
            findNavController().setGraph(R.navigation.overall_navigation, null)
            dismiss()
        })
    }
}
package com.nkuppan.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.ancient.essentials.extentions.EventObserver
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTaskCreateDialogBinding
import com.nkuppan.todo.ui.viewmodel.TaskCreateViewModel

class TaskCreateFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: TaskCreateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_create_dialog, container, false)
        viewModel = ViewModelProvider(this).get(TaskCreateViewModel::class.java)
        val binding = FragmentTaskCreateDialogBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.success.observe(viewLifecycleOwner, EventObserver {
            dismiss()
        })
    }

    companion object {
        fun newInstance() = TaskCreateFragment()
    }
}
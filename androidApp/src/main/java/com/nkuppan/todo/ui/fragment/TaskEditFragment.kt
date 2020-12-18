package com.nkuppan.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ancient.essentials.extentions.EventObserver
import com.ancient.essentials.extentions.autoCleared
import com.ancient.essentials.view.fragment.BaseFragment
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTaskCreateBinding
import com.nkuppan.todo.ui.viewmodel.TaskCreateViewModel

class TaskEditFragment : BaseFragment() {

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

        viewModel.success.observe(viewLifecycleOwner, EventObserver {

        })
    }
}
package com.nkuppan.todo.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ancient.essentials.extentions.EventObserver
import com.ancient.essentials.extentions.autoCleared
import com.ancient.essentials.view.fragment.BaseFragment
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTaskCreateBinding
import com.nkuppan.todo.databinding.FragmentTaskEditBinding
import com.nkuppan.todo.ui.viewmodel.TaskCreateViewModel

class TaskEditFragment : BaseFragment() {

    private var viewModel: TaskCreateViewModel by autoCleared()

    private var dataBinding: FragmentTaskEditBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_edit, container, false)
        viewModel = ViewModelProvider(this).get(TaskCreateViewModel::class.java)
        val binding = FragmentTaskCreateBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (requireActivity() as AppCompatActivity).setSupportActionBar(dataBinding.toolbar)

        setHasOptionsMenu(true)

        super.onViewCreated(view, savedInstanceState)

        dataBinding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_save) {
                //viewModel.createOrUpdateTaskGroup()
                return@setOnMenuItemClickListener true
            }

            return@setOnMenuItemClickListener false
        }

        dataBinding.taskTitle.setOnEditorActionListener { _, aActionId, _ ->
            if (aActionId == EditorInfo.IME_ACTION_DONE) {
                //viewModel.createOrUpdateTaskGroup()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        dataBinding.toolbar.setNavigationOnClickListener {
            requireActivity().setResult(Activity.RESULT_CANCELED)
            requireActivity().finish()
        }

        viewModel.success.observe(viewLifecycleOwner, EventObserver {

        })

        viewModel.selectDateTimeClick()
    }
}
package com.nkuppan.todo.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ancient.essentials.extentions.EventObserver
import com.ancient.essentials.extentions.autoCleared
import com.ancient.essentials.extentions.setupStringSnackbar
import com.google.android.material.snackbar.Snackbar
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTaskGroupEditBinding
import com.nkuppan.todo.ui.viewmodel.TaskGroupCreateViewModel
import com.nkuppan.todo.utils.RequestParam

class TaskGroupCreateFragment : Fragment() {

    private var viewModel: TaskGroupCreateViewModel by autoCleared()

    private var dataBinding: FragmentTaskGroupEditBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_group_edit, container, false)
        viewModel = ViewModelProvider(this).get(TaskGroupCreateViewModel::class.java)
        dataBinding = FragmentTaskGroupEditBinding.bind(view)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (requireActivity() as AppCompatActivity).setSupportActionBar(dataBinding.toolbar)

        setHasOptionsMenu(true)

        super.onViewCreated(view, savedInstanceState)

        dataBinding.root.setupStringSnackbar(
            this,
            viewModel.snackBarMessage,
            Snackbar.LENGTH_SHORT
        )

        viewModel.success.observe(viewLifecycleOwner, EventObserver {
            requireActivity().setResult(Activity.RESULT_OK)
            requireActivity().finish()
        })

        viewModel.taskGroupName.observe(viewLifecycleOwner, Observer {
            dataBinding.toolbar.menu.findItem(R.id.action_save)?.isEnabled =
                it?.isNotBlank() == true
        })

        dataBinding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_save) {
                viewModel.createOrUpdateTaskGroup()
                return@setOnMenuItemClickListener true
            }

            return@setOnMenuItemClickListener false
        }

        dataBinding.groupName.setOnEditorActionListener { _, aActionId, _ ->
            if (aActionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.createOrUpdateTaskGroup()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        dataBinding.toolbar.setNavigationOnClickListener {
            requireActivity().setResult(Activity.RESULT_CANCELED)
            requireActivity().finish()
        }

        viewModel.updateTaskGroupValue(
            requireActivity().intent?.extras?.getString(RequestParam.TASK_GROUP_ID)
        )
    }
}
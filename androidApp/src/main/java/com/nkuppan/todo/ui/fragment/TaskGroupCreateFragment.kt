package com.nkuppan.todo.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTaskGroupEditBinding
import com.nkuppan.todo.extention.EventObserver
import com.nkuppan.todo.extention.autoCleared
import com.nkuppan.todo.extention.setupStringSnackbar
import com.nkuppan.todo.ui.viewmodel.TaskGroupCreateViewModel
import com.nkuppan.todo.utils.RequestParam

class TaskGroupCreateFragment : Fragment() {

    private var viewModel: TaskGroupCreateViewModel by autoCleared()

    private var dataBinding: FragmentTaskGroupEditBinding by autoCleared()

    private var menu: Menu? = null

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_task_group, menu)
        this.menu = menu
        enableSaveButton(viewModel.taskGroupName.value)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                viewModel.createOrUpdateTaskGroup()
                true
            }
            else -> {
                false
            }
        }
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

        viewModel.taskGroupName.observe(viewLifecycleOwner, { aValue ->
            enableSaveButton(aValue)
        })

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

    private fun enableSaveButton(aValue: String?) {
        menu?.findItem(R.id.action_save)?.let {
            it.isEnabled = aValue?.isNotBlank() == true
        }
    }
}
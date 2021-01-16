package com.nkuppan.todo.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTaskEditBinding
import com.nkuppan.todo.extention.EventObserver
import com.nkuppan.todo.extention.autoCleared
import com.nkuppan.todo.ui.viewmodel.TaskCreateViewModel
import com.nkuppan.todo.utils.AppUIUtils
import com.nkuppan.todo.utils.NavigationManager.setSuccessResult
import com.nkuppan.todo.utils.RequestParam

class TaskEditFragment : BaseFragment() {

    private var viewModel: TaskCreateViewModel by autoCleared()

    private var dataBinding: FragmentTaskEditBinding by autoCleared()

    private var menu: Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_edit, container, false)
        viewModel = ViewModelProvider(this).get(TaskCreateViewModel::class.java)
        dataBinding = FragmentTaskEditBinding.bind(view)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_task_edit, menu)
        this.menu = menu
        enableRevertOrDone()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_done -> {
                viewModel.markThisTaskAsCompleted()
                true
            }
            R.id.action_revert -> {
                viewModel.revertThisTask()
                true
            }
            R.id.action_delete -> {
                viewModel.deleteThisTask()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (requireActivity() as AppCompatActivity).setSupportActionBar(dataBinding.toolbar)

        setHasOptionsMenu(true)

        super.onViewCreated(view, savedInstanceState)

        dataBinding.toolbar.setNavigationOnClickListener {
            requireActivity().setResult(Activity.RESULT_CANCELED)
            requireActivity().finish()
        }

        viewModel.taskGroupList.observe(viewLifecycleOwner, EventObserver {
            dataBinding.taskGroupList.adapter = ArrayAdapter(
                requireContext(), R.layout.spinner_item, it
            )
        })

        viewModel.selectDateTime.observe(viewLifecycleOwner, EventObserver {
            AppUIUtils.showDatePickerDialog(this, viewModel.taskEndDate) {
                viewModel.updateDateTime(it)
            }
        })

        viewModel.success.observe(viewLifecycleOwner, EventObserver {
            setSuccessResult()
        })

        viewModel.deleted.observe(viewLifecycleOwner, EventObserver {
            setSuccessResult()
        })

        viewModel.updateMenu.observe(viewLifecycleOwner, EventObserver {
            enableRevertOrDone()
        })

        viewModel.loadTaskDetails(
            requireActivity().intent?.extras?.getString(RequestParam.TASK_ID)
        )

        viewModel.loadTaskGroupList()

        dataBinding.taskDescription.addTextChangedListener {
            viewModel.createTaskClick()
        }

        dataBinding.taskTitle.addTextChangedListener {
            viewModel.createTaskClick()
        }
    }

    private fun enableRevertOrDone() {
        val isCompleted = viewModel.isCompleted()
        menu?.findItem(R.id.action_revert)?.apply {
            isVisible = isCompleted
        }
        menu?.findItem(R.id.action_done)?.apply {
            isVisible = !isCompleted
        }
    }
}
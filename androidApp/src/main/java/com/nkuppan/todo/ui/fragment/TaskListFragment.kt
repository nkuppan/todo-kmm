package com.nkuppan.todo.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTodoListBinding
import com.nkuppan.todo.db.Task
import com.nkuppan.todo.extention.autoCleared
import com.nkuppan.todo.ui.adapter.CompletedTaskListAdapter
import com.nkuppan.todo.ui.adapter.PendingTaskListAdapter
import com.nkuppan.todo.ui.viewmodel.TaskListViewModel
import com.nkuppan.todo.utils.NavigationManager
import com.nkuppan.todo.utils.NavigationManager.relaunchMainScreen

class TaskListFragment : BaseFragment() {

    private var binding: FragmentTodoListBinding by autoCleared()

    private var viewModel: TaskListViewModel by autoCleared()

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                relaunchMainScreen()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo_list, container, false)
        viewModel = ViewModelProvider(this).get(TaskListViewModel::class.java)
        binding = FragmentTodoListBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refreshLayout.setOnRefreshListener {
            viewModel.loadTaskList()
        }

        loadPendingTask()

        loadCompletedTasks()

        viewModel.taskGroupNameList.observe(this.viewLifecycleOwner, {
            viewModel.loadTaskList()
        })

        viewModel.loadTaskGroup()
    }

    private fun loadPendingTask() {
        val adapter = PendingTaskListAdapter { aTask: Task, aType: Int ->
            if (aType == 1) {
                NavigationManager.openTaskEditPage(this, aTask.id, resultLauncher)
            } else if (aType == 2) {
                viewModel.saveThisTaskAsCompleted(aTask)
            }
        }

        binding.pendingTaskList.adapter = adapter

        viewModel.taskList.observe(this.viewLifecycleOwner, {
            adapter.submitList(it)
            binding.refreshLayout.isRefreshing = false
        })
    }

    private fun loadCompletedTasks() {
        val completedTaskAdapter = CompletedTaskListAdapter { aTask: Task, aType: Int ->
            if (aType == 1) {
                NavigationManager.openTaskEditPage(this, aTask.id, resultLauncher)
            }
        }

        binding.completedTaskList.adapter = completedTaskAdapter

        viewModel.completedList.observe(this.viewLifecycleOwner, {
            completedTaskAdapter.submitList(it)
            binding.refreshLayout.isRefreshing = false
        })
    }
}
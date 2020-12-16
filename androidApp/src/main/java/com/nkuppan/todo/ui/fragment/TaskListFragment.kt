package com.nkuppan.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ancient.essentials.extentions.autoCleared
import com.ancient.essentials.view.fragment.BaseFragment
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTodoListBinding
import com.nkuppan.todo.db.Task
import com.nkuppan.todo.ui.adapter.CompletedTaskListAdapter
import com.nkuppan.todo.ui.adapter.PendingTaskListAdapter
import com.nkuppan.todo.ui.viewmodel.TaskListViewModel

class TaskListFragment : BaseFragment() {

    private var binding: FragmentTodoListBinding by autoCleared()

    private var viewModel: TaskListViewModel by autoCleared()

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
        val adapter = PendingTaskListAdapter { task: Task, i: Int ->
            if (i == 1) {
                //TODO
            } else if (i == 2) {
                viewModel.saveThisTaskAsCompleted(task)
            }
        }

        binding.pendingTaskList.adapter = adapter

        viewModel.taskList.observe(this.viewLifecycleOwner, {
            adapter.submitList(it)
            binding.refreshLayout.isRefreshing = false
        })
    }

    private fun loadCompletedTasks() {
        val completedTaskAdapter = CompletedTaskListAdapter { task: Task, i: Int ->
            //TODO
        }

        binding.completedTaskList.adapter = completedTaskAdapter

        viewModel.completedList.observe(this.viewLifecycleOwner, {
            completedTaskAdapter.submitList(it)
            binding.refreshLayout.isRefreshing = false
        })
    }
}
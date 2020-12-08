package com.nkuppan.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTodoListBinding
import com.nkuppan.todo.db.Task
import com.nkuppan.todo.ui.adapter.TaskListAdapter
import com.nkuppan.todo.ui.viewmodel.TaskListViewModel

class TaskListFragment : Fragment() {

    private lateinit var binding: FragmentTodoListBinding

    private lateinit var viewModel: TaskListViewModel

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

        val adapter = TaskListAdapter { task: Task, i: Int ->
            //TODO edit the todo task item
        }

        binding.taskList.adapter = adapter

        viewModel.taskList.observe(this.viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.loadTaskList()
    }

    companion object {
        fun newInstance() = TaskListFragment()
    }
}
package com.nkuppan.todo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.ancient.essentials.view.adapter.DataBoundListAdapter
import com.nkuppan.todo.databinding.AdapterTodoItemBinding
import com.nkuppan.todo.model.Task

class TaskListAdapter(private val listener: ((Task, Int) -> Unit)?) :
    DataBoundListAdapter<Task, AdapterTodoItemBinding>(
        diffCallback = object : DiffUtil.ItemCallback<Task>() {

            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.title == newItem.title &&
                        oldItem.description == newItem.description &&
                        oldItem.status == newItem.status &&
                        oldItem.created_on == newItem.created_on &&
                        oldItem.updated_on == newItem.updated_on
            }
        }
    ) {

    override fun bind(binding: AdapterTodoItemBinding, item: Task) {
        binding.viewModel = item
    }

    override fun createBinding(parent: ViewGroup): AdapterTodoItemBinding {
        val dataBinding = AdapterTodoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        dataBinding.root.setOnClickListener {
            dataBinding.viewModel?.let {
                listener?.invoke(it, 2)
            }
        }

        return dataBinding
    }
}
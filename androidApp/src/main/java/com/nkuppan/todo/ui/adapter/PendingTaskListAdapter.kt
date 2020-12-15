package com.nkuppan.todo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.ancient.essentials.view.adapter.DataBoundListAdapter
import com.nkuppan.todo.databinding.AdapterPendingTodoItemBinding
import com.nkuppan.todo.model.Task

class PendingTaskListAdapter(private val listener: ((Task, Int) -> Unit)?) :
    DataBoundListAdapter<Task, AdapterPendingTodoItemBinding>(
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

    override fun bind(binding: AdapterPendingTodoItemBinding, item: Task) {
        binding.viewModel = item
    }

    override fun createBinding(parent: ViewGroup): AdapterPendingTodoItemBinding {
        val dataBinding = AdapterPendingTodoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        dataBinding.root.setOnClickListener {
            dataBinding.viewModel?.let {
                listener?.invoke(it, 1)
            }
        }

        dataBinding.markComplete.setOnCheckedChangeListener { aView, aStatus ->
            dataBinding.viewModel?.let {
                listener?.invoke(it, 2)
            }
        }

        return dataBinding
    }
}
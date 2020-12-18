package com.nkuppan.todo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.ancient.essentials.view.adapter.DataBoundListAdapter
import com.nkuppan.todo.databinding.AdapterGroupListItemBinding
import com.nkuppan.todo.db.TaskGroup

class TaskGroupListAdapter(private val listener: ((TaskGroup, Int) -> Unit)?) :
    DataBoundListAdapter<TaskGroup, AdapterGroupListItemBinding>(
        diffCallback = object : DiffUtil.ItemCallback<TaskGroup>() {

            override fun areItemsTheSame(oldItem: TaskGroup, newItem: TaskGroup): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TaskGroup, newItem: TaskGroup): Boolean {
                return oldItem.group_name == newItem.group_name &&
                        oldItem.created_on == newItem.created_on &&
                        oldItem.updated_on == newItem.updated_on
            }
        }
    ) {

    override fun bind(binding: AdapterGroupListItemBinding, item: TaskGroup) {
        binding.viewModel = item
    }

    override fun createBinding(parent: ViewGroup): AdapterGroupListItemBinding {

        val dataBinding = AdapterGroupListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        dataBinding.container.setOnClickListener {
            dataBinding.viewModel?.let {
                listener?.invoke(it, 1)
            }
        }

        return dataBinding
    }
}
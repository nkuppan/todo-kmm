package com.nkuppan.todo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.nkuppan.todo.databinding.AdapterTaskGroupSelectionItemBinding
import com.nkuppan.todo.db.TaskGroup

class TaskGroupSelectionListAdapter(
    private val selectedTaskGroupId: String?,
    private val listener: ((TaskGroup, Int) -> Unit)?
) :
    DataBoundListAdapter<TaskGroup, AdapterTaskGroupSelectionItemBinding>(
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

    override fun bind(binding: AdapterTaskGroupSelectionItemBinding, item: TaskGroup) {
        binding.isSelected = item.id == selectedTaskGroupId
        binding.viewModel = item
    }

    override fun createBinding(parent: ViewGroup): AdapterTaskGroupSelectionItemBinding {

        val dataBinding = AdapterTaskGroupSelectionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        dataBinding.root.setOnClickListener {
            dataBinding.viewModel?.let {
                listener?.invoke(it, 1)
            }
        }

        return dataBinding
    }
}
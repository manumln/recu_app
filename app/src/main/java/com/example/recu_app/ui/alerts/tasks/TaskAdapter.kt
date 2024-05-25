package com.example.recu_app.ui.alerts.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recu_app.R
import com.example.recu_app.databinding.TaskListItemBinding
import com.example.recu_app.data.Task
import com.example.recu_app.util.Util.formattedDate

class TaskAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Task, TaskAdapter.TasksViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding =
            TaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class TasksViewHolder(private val binding: TaskListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onItemClick(task)
                    }
                }

                imageButton.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onCheckBoxClick(task, !task.expired)
                    }
                }
            }
        }

        fun bind(task: Task) {
            binding.apply {
                imageButton.setImageResource(if (task.expired) R.drawable.delete_ic_baseline_delete_24 else R.drawable.delete_ic_baseline_delete_24)
                txtTitle.text = task.name
                txtTitle.paint.isStrikeThruText = task.expired

                if (!task.expired) {
                    if (task.dueDate.toInt() != 0) {
                        chipDate.isVisible = true
                        chipDate.text = task.dueDate.formattedDate()
                    } else chipDate.isVisible = false
                } else chipDate.isVisible = false
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(task: Task)
        fun onCheckBoxClick(task: Task, isChecked: Boolean)
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) =
            oldItem == newItem
    }
}
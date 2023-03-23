package com.example.ustudy.ui.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ustudy.data.local.Task
import com.example.ustudy.databinding.TaskItemBinding

class TaskAdapter(private val taskList: List<Task>, private val onDeleteCallback: (Task) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(private val taskBinding: TaskItemBinding) :
        RecyclerView.ViewHolder(taskBinding.root) {
        fun bind(task: Task) {
            taskBinding.task.text = task.task
            taskBinding.ivRemoveTask.setOnClickListener {
                onDeleteCallback(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }
}
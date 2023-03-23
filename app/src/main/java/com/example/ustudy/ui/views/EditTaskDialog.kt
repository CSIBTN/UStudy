package com.example.ustudy.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.ustudy.data.local.Task
import com.example.ustudy.databinding.DialogEditTaskBinding
import com.example.ustudy.ui.viewmodels.TasksViewModel
import com.example.ustudy.util.IDHandler
import com.example.ustudy.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditTaskDialog : DialogFragment() {
    private var _editDialogBinding: DialogEditTaskBinding? = null
    private val editDialogBinding: DialogEditTaskBinding
        get() = checkNotNull(_editDialogBinding) {
            Util.bindingErrorMessage("edit-task")
        }
    private val taskViewModel: TasksViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _editDialogBinding = DialogEditTaskBinding.inflate(inflater, container, false)

        editDialogBinding.btnSave.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val day = requireArguments().getInt("day")
                val month = requireArguments().getString("month")
                val year = requireArguments().getInt("year")
                val task = month?.let {
                    Task(
                        IDHandler.getId(),
                        day, year, it,
                        editDialogBinding.taskText.text.toString()
                    )
                }
                if (task != null) {
                    taskViewModel.insertTask(task)
                }
            }
        }
        return editDialogBinding.root
    }
}
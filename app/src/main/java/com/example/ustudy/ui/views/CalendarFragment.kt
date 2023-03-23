package com.example.ustudy.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ustudy.R
import com.example.ustudy.data.local.Task
import com.example.ustudy.databinding.CalendarItemBinding
import com.example.ustudy.ui.viewmodels.TasksViewModel
import com.example.ustudy.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class CalendarFragment : Fragment() {
    private var _calendarBinding: CalendarItemBinding? = null
    private val calendarBinding: CalendarItemBinding
        get() = checkNotNull(_calendarBinding) {
            Util.bindingErrorMessage("calendar")
        }
    private val taskViewModel: TasksViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _calendarBinding = CalendarItemBinding.inflate(inflater, container, false)
        calendarBinding.calendar.onDateClickedCallback = onDateClickedCallback
        calendarBinding.calendarMonth.text = LocalDate.now().month.toString()
        calendarBinding.rvTasks.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        return calendarBinding.root
    }

    private val onDeleteTask: (Task) -> Unit = { task ->
        CoroutineScope(Dispatchers.IO).launch {
            taskViewModel.deleteTask(task)
        }
    }

    private val onDateClickedCallback: (Int) -> Unit = { day ->
        calendarBinding.calendarDay.text = "Day $day"
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                taskViewModel.getTasks(day, LocalDate.now().month.toString(), LocalDate.now().year)
                    .collect { tasks ->
                        calendarBinding.rvTasks.adapter = TaskAdapter(tasks, onDeleteTask)
                        calendarBinding.addTaskIcon.visibility = View.VISIBLE
                        calendarBinding.addTaskIcon.setOnClickListener {
                            findNavController().navigate(
                                R.id.createTask,
                                args = bundleOf(
                                    "day" to day,
                                    "month" to LocalDate.now().month.toString(),
                                    "year" to LocalDate.now().year
                                )
                            )
                        }
                    }
            }
        }
    }

}
package com.example.ustudy.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.ustudy.data.local.StudyRepository
import com.example.ustudy.data.local.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val studyRepository: StudyRepository
) : ViewModel() {
    private val _currentTaskList: MutableStateFlow<List<Task>> = MutableStateFlow(
        emptyList()
    )

    suspend fun getTasks(day: Int, month: String, year: Int) =
        studyRepository.getTasks(day, month, year)

    suspend fun deleteTask(task: Task) = studyRepository.deleteTask(task)

    suspend fun insertTask(task: Task) = studyRepository.insertTask(task)
}
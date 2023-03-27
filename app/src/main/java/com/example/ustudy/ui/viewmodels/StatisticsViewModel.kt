package com.example.ustudy.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ustudy.data.local.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    private val _currentStreak: MutableStateFlow<Int> = MutableStateFlow(1)
    val currentStreak = _currentStreak.asStateFlow()

    private val _daysLearned: MutableStateFlow<Int> = MutableStateFlow(1)
    val daysLearned = _daysLearned.asStateFlow()

    init {
        viewModelScope.launch {
            updateStatistics()
            sharedPreferencesRepository.getStreak().collectLatest { streak ->
                _currentStreak.update {
                    streak
                }
            }
            sharedPreferencesRepository.getDaysLearned().collectLatest { daysLearned ->
                _daysLearned.update {
                    daysLearned
                }
            }
        }
    }

    private suspend fun updateStatistics() = sharedPreferencesRepository.setNewCurrentDate()
}
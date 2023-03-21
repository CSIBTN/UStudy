package com.example.ustudy.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.ustudy.util.Util.DEFAULT_POMODORO_TIME

class PomodoroViewModel : ViewModel() {
    var time = DEFAULT_POMODORO_TIME
        private set
    var isFirstTimeSelected = true
        private set

    fun updateTime(newTime: Double) {
        time = newTime
    }

    fun setFirstTimeSelected(isSelected: Boolean) {
        isFirstTimeSelected = isSelected
    }

}
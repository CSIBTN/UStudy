package com.example.ustudy.ui.views

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ustudy.R
import com.example.ustudy.TimerService
import com.example.ustudy.databinding.FragmentPomodoroBinding
import com.example.ustudy.ui.viewmodels.PomodoroViewModel
import com.example.ustudy.util.UStudyApplication
import com.example.ustudy.util.Util
import com.example.ustudy.util.Util.CRAMMING_POMODORO_TIME
import com.example.ustudy.util.Util.DEFAULT_POMODORO_TIME
import com.example.ustudy.util.Util.INTERMEDIATE_POMODORO_TIME
import com.example.ustudy.util.Util.LONG_POMODORO_TIME
import com.example.ustudy.util.Util.POMODORO_NOTIFICATION_ID
import com.example.ustudy.util.Util.getTimeStringFromDouble

class PomodoroFragment : Fragment() {
    private var _pomodoroBinding: FragmentPomodoroBinding? = null
    private val pomodoroBinding: FragmentPomodoroBinding
        get() = checkNotNull(_pomodoroBinding) {
            Util.bindingErrorMessage("pomodoro")
        }

    private val pomodoroViewModel: PomodoroViewModel by viewModels()
    private lateinit var serviceIntent: Intent
    private val notificationManager =
        UStudyApplication.getApplicationContext()
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val timerBroadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent) {
            pomodoroViewModel.updateTime(
                intent.getDoubleExtra(
                    TimerService.TIME_EXTRA,
                    INTERMEDIATE_POMODORO_TIME
                )
            )
            notificationManager.notify(
                POMODORO_NOTIFICATION_ID,
                TimerService.createNotification(pomodoroViewModel.time)
            )
            pomodoroBinding.textView.text = getTimeStringFromDouble(pomodoroViewModel.time)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _pomodoroBinding = FragmentPomodoroBinding.inflate(inflater, container, false)
        setUpTimerVariables()
        setUpSpinnerListeners()
        setUpButtonListeners()
        setUpSpinnerAdapter()
        return pomodoroBinding.root
    }


    private fun setUpTimerVariables() {
        pomodoroViewModel.setFirstTimeSelected(true)
        serviceIntent = Intent(requireActivity().applicationContext, TimerService::class.java)
        requireActivity().registerReceiver(
            timerBroadCastReceiver,
            IntentFilter(TimerService.TIMER_UPDATED)
        )
    }

    private fun setUpButtonListeners() {
        pomodoroBinding.btnStartTimer.setOnClickListener {
            startTimer()
        }
        pomodoroBinding.btnPauseTimer.setOnClickListener {
            pauseTimer()
        }
        pomodoroBinding.btnStopTimer.setOnClickListener {
            stopTimer()
        }
    }

    private fun setUpSpinnerListeners() {
        pomodoroBinding.spTimer.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    if (!pomodoroViewModel.isFirstTimeSelected) {
                        pauseTimer()
                    }
                    when (position) {
                        0 -> pomodoroViewModel.updateTime(DEFAULT_POMODORO_TIME)
                        1 -> pomodoroViewModel.updateTime(INTERMEDIATE_POMODORO_TIME)
                        2 -> pomodoroViewModel.updateTime(LONG_POMODORO_TIME)
                        3 -> pomodoroViewModel.updateTime(CRAMMING_POMODORO_TIME)
                    }
                    pomodoroBinding.textView.text = getTimeStringFromDouble(pomodoroViewModel.time)
                    pomodoroViewModel.setFirstTimeSelected(false)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
    }

    private fun setUpSpinnerAdapter() {
        val spinner = pomodoroBinding.spTimer
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.timer_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }


    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, pomodoroViewModel.time)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().startForegroundService(serviceIntent)
        } else {
            requireActivity().startService(serviceIntent)
        }
    }

    private fun pauseTimer() {
        requireActivity().stopService(serviceIntent)
    }

    private fun stopTimer() {
        pauseTimer()
        pomodoroViewModel.updateTime(DEFAULT_POMODORO_TIME)
        pomodoroBinding.textView.text = getTimeStringFromDouble(pomodoroViewModel.time)
    }
}
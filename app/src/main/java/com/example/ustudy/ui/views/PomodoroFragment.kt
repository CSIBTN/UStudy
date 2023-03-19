package com.example.ustudy.ui.views

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
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.ustudy.R
import com.example.ustudy.TimerService
import com.example.ustudy.databinding.FragmentPomodoroBinding
import com.example.ustudy.util.Util
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class PomodoroFragment : Fragment() {
    private var _pomodoroBinding: FragmentPomodoroBinding? = null
    private val pomodoroBinding: FragmentPomodoroBinding
        get() = checkNotNull(_pomodoroBinding) {
            Util.bindingErrorMessage("pomodoro")
        }
    private lateinit var serviceIntent: Intent
    private var time by Delegates.notNull<Double>()
    private var isFirstTimeSelected by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isFirstTimeSelected = true
        _pomodoroBinding = FragmentPomodoroBinding.inflate(inflater, container, false)
        serviceIntent = Intent(requireActivity().applicationContext, TimerService::class.java)
        requireActivity().registerReceiver(
            timerBroadCastReceiver,
            IntentFilter(TimerService.TIMER_UPDATED)
        )
        pomodoroBinding.spTimer.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    if (!isFirstTimeSelected) {
                        pauseTimer()
                    }
                    when (position) {
                        0 -> time = 1500.0
                        1 -> time = 1800.0
                        2 -> time = 3600.0
                        3 -> time = 5400.0
                    }
                    pomodoroBinding.textView.text = getTimeStringFromDouble(time)
                    isFirstTimeSelected = false
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        return pomodoroBinding.root
    }

    private val timerBroadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            pomodoroBinding.textView.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86488 / 3600
        val minutes = resultInt % 86488 % 3600 / 60
        val seconds = resultInt % 86488 % 3600 % 60
        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Int, minutes: Int, seconds: Int): String =
        String.format("%02d:%02d:%02d", hours, minutes, seconds)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pomodoroBinding.btnStartTimer.setOnClickListener {
            startTimer()
        }
        pomodoroBinding.btnPauseTimer.setOnClickListener {
            pauseTimer()
        }
        pomodoroBinding.btnStopTimer.setOnClickListener {
            stopTimer()
        }

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        requireActivity().startForegroundService(serviceIntent)
    }

    private fun pauseTimer() {
        requireActivity().stopService(serviceIntent)
    }

    private fun stopTimer() {
        pauseTimer()
        time = 1500.0
        pomodoroBinding.textView.text = getTimeStringFromDouble(time)
    }

}
package com.example.ustudy

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.ustudy.util.UStudyApplication
import java.util.*

class TimerService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    private val timer = Timer()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(TIME_EXTRA, 0.0)
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)
        val channel =
            NotificationChannel("pomodoro_channel", "pomodoro", NotificationManager.IMPORTANCE_HIGH)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification =
            Notification.Builder(UStudyApplication.getApplicationContext(), "pomodoro_channel")
                .setContentTitle("FOREGROUND SERVICE")
                .setContentText("...")
                .build()
        startForeground(1, notification)
        return START_NOT_STICKY
    }

    private inner class TimeTask(private var time: Double) : TimerTask() {
        override fun run() {
            val intent = Intent(TIMER_UPDATED)
            time--
            intent.putExtra(TIME_EXTRA, time)
            sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    companion object {
        const val TIMER_UPDATED = "timerUpdated"
        const val TIME_EXTRA = "timeExtra"
    }
}
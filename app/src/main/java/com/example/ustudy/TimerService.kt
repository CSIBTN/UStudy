package com.example.ustudy

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.ustudy.util.UStudyApplication
import com.example.ustudy.util.Util
import com.example.ustudy.util.Util.POMODORO_NOTIFICATION_ID
import com.example.ustudy.util.Util.getTimeStringFromDouble
import java.util.*

class TimerService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    private val notificationManager =
        UStudyApplication.getApplicationContext()
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val timer = Timer()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(TIME_EXTRA, 0.0)
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)

        startForeground(POMODORO_NOTIFICATION_ID, createNotification(time))
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


    companion object {
        const val TIMER_UPDATED = "timerUpdated"
        const val TIME_EXTRA = "timeExtra"

        fun createNotification(time: Double): Notification =
            NotificationCompat.Builder(
                UStudyApplication.getApplicationContext(),
                Util.POMODORO_CHANNEL_ID
            )
                .setContentTitle("The timer has been started")
                .setSmallIcon(R.drawable.tomato_ic)
                .setContentText(getTimeStringFromDouble(time))
                .build()

    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }
}
package com.example.ustudy.util

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class UStudyApplication : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    Util.POMODORO_CHANNEL_ID,
                    "Pomodoro",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    companion object {
        private var instance: UStudyApplication? = null
        fun getApplicationContext(): Context {
            return instance!!.applicationContext
        }
    }

}
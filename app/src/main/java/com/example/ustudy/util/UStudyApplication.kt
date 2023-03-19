package com.example.ustudy.util

import android.app.Application
import android.content.Context

class UStudyApplication : Application() {
    init {
        instance = this
    }

    companion object {
        var instance: UStudyApplication? = null
        fun getApplicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}
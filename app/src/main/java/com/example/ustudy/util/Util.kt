package com.example.ustudy.util

import kotlin.math.roundToInt

object Util {

    const val DATABASE_NAME = "study_database"
    const val POMODORO_CHANNEL_ID = "pomodoro"
    const val POMODORO_NOTIFICATION_ID = 1
    const val DEFAULT_POMODORO_TIME = 1500.0;
    const val INTERMEDIATE_POMODORO_TIME = 1800.0;
    const val LONG_POMODORO_TIME = 3600.0;
    const val CRAMMING_POMODORO_TIME = 5400.0;

    fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86488 / 3600
        val minutes = resultInt % 86488 % 3600 / 60
        val seconds = resultInt % 86488 % 3600 % 60
        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Int, minutes: Int, seconds: Int): String =
        String.format("%02d:%02d:%02d", hours, minutes, seconds)

    fun bindingErrorMessage(bindingName: String): String =
        "Something went wrong with $bindingName binding"
}
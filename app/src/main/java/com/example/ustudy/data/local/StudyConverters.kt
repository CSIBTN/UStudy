package com.example.ustudy.data.local

import androidx.room.TypeConverter

object StudyConverters {

    @TypeConverter
    fun fromList(list: List<String>): String {
        val stringLine = StringBuilder()
        for (item in list) {
            stringLine.append(item).append(",")
        }
        return stringLine.toString()
    }

    @TypeConverter
    fun fromString(str: String): List<String> = str.split(",")


}
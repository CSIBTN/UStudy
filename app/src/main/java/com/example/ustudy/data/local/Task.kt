package com.example.ustudy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey
    val id: Int,
    val day: Int,
    val year: Int,
    val month: String,
    val task: String
)

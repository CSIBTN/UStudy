package com.example.ustudy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey
    val id: Int,
    val title: String,
    val content: String,
    val lastUpdatedDate: String
)

package com.example.ustudy.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey
    val id: Int,
    val title: String,
    val content: String,
    val lastUpdatedDate: String
) : Parcelable

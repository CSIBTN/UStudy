package com.example.ustudy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([Note::class], version = 1)
abstract class StudyDatabase : RoomDatabase() {
    abstract val notesDao: NotesDao
}
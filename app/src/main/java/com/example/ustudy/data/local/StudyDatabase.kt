package com.example.ustudy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    [Note::class, Task::class],
    version = 3
)
@TypeConverters(StudyConverters::class)
abstract class StudyDatabase : RoomDatabase() {
    abstract val notesDao: NotesDao
    abstract val tasksDao: TasksDao
}
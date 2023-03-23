package com.example.ustudy.data.local

import kotlinx.coroutines.flow.Flow

interface StudyRepository {

    suspend fun deleteTask(task: Task)
    suspend fun insertTask(task: Task)
    suspend fun getTasks(day: Int, month: String, year: Int): Flow<List<Task>>
    suspend fun getAllNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
}
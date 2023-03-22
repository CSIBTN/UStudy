package com.example.ustudy.data.local

import kotlinx.coroutines.flow.Flow

interface StudyRepository {
    suspend fun getAllNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note)
}
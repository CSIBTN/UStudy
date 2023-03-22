package com.example.ustudy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): Flow<List<Note>>

    @Insert
    suspend fun insertNote(note: Note)

}
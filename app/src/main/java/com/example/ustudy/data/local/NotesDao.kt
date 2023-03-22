package com.example.ustudy.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note : Note)
}
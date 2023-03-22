package com.example.ustudy.data.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudyImp @Inject constructor(
    private val studyDatabase: StudyDatabase
) : StudyRepository {


    override suspend fun getAllNotes(): Flow<List<Note>> = studyDatabase.notesDao.getAllNotes()

    override suspend fun insertNote(note: Note) = studyDatabase.notesDao.insertNote(note)

}
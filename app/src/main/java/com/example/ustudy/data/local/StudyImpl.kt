package com.example.ustudy.data.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class StudyImpl @Inject constructor(
    private val studyDatabase: StudyDatabase
) : StudyRepository {
    override suspend fun deleteTask(task: Task) = studyDatabase.tasksDao.deleteTask(task)

    override suspend fun insertTask(task: Task) = studyDatabase.tasksDao.insertTask(task)

    override suspend fun getTasks(day: Int, month: String, year: Int): Flow<List<Task>> =
        studyDatabase.tasksDao.getTasks(day, month, year)

    override suspend fun getAllNotes(): Flow<List<Note>> = studyDatabase.notesDao.getAllNotes()

    override suspend fun insertNote(note: Note) = studyDatabase.notesDao.insertNote(note)
    override suspend fun deleteNote(note: Note) = studyDatabase.notesDao.deleteNote(note)

}
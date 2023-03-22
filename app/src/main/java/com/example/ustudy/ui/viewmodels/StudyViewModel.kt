package com.example.ustudy.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ustudy.data.local.Note
import com.example.ustudy.data.local.StudyRepository
import com.example.ustudy.util.IDHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyViewModel
@Inject constructor(private val studyRepository: StudyRepository) : ViewModel() {

    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())
    val notes: StateFlow<List<Note>>
        get() = _notes.asStateFlow()

    init {
        viewModelScope.launch {
            getAllNotes().collect { nNotes ->
                _notes.update { nNotes }
            }
        }
    }

    suspend fun createNewNote(
        id: Int = IDHandler.getId(),
        title: String,
        content: String,
        date: String
    ) {
        addNote(
            Note(
                id,
                title,
                content,
                date
            )
        )
    }

    private suspend fun getAllNotes(): Flow<List<Note>> = studyRepository.getAllNotes()

    private suspend fun addNote(note: Note) = studyRepository.insertNote(note)

    suspend fun deleteNote(note: Note) = studyRepository.deleteNote(note)
}
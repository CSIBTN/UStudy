package com.example.ustudy.ui.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ustudy.data.local.Note
import com.example.ustudy.databinding.FragmentNoteEditorBinding
import com.example.ustudy.ui.viewmodels.NotesViewModel
import com.example.ustudy.util.Util
import com.example.ustudy.util.Util.NOTE_TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

@AndroidEntryPoint
class NoteEditorFragment : Fragment() {

    private var _noteEditorBinding: FragmentNoteEditorBinding? = null
    private val noteEditorBinding: FragmentNoteEditorBinding
        get() = checkNotNull(_noteEditorBinding) {
            Util.bindingErrorMessage("notes-editor")
        }
    private val notesViewModel: NotesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _noteEditorBinding = FragmentNoteEditorBinding.inflate(inflater, container, false)
        setEditFieldsIfNeeded()
        setUpSaveNoteListener()
        return noteEditorBinding.root
    }

    private fun setUpSaveNoteListener() {
        noteEditorBinding.ivFinishedEditingIcon.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    val title = noteEditorBinding.tiTitle.text.toString()
                    val content = noteEditorBinding.tiNoteContent.text.toString()
                    val date = "${LocalDate.now().month} ${LocalDate.now().dayOfMonth} ${
                        LocalDateTime.now().toLocalTime().hour
                    }:${LocalDateTime.now().toLocalTime().minute}"
                    notesViewModel.createNewNote(title = title, content = content, date = date)
                }
            }
        }
    }

    private fun setEditFieldsIfNeeded() {
        try {
            val noteToEdit = requireArguments().getParcelable<Note>(NOTE_TAG)
            if (noteToEdit != null) {
                noteEditorBinding.tiTitle.setText(noteToEdit.title)
                noteEditorBinding.tiNoteContent.setText(noteToEdit.content)
            }
        } catch (e: Exception) {
            Log.e("NoArguments", "$e")
        }
    }
}
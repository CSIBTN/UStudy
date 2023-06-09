package com.example.ustudy.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ustudy.R
import com.example.ustudy.data.local.Note
import com.example.ustudy.databinding.FragmentNotesBinding
import com.example.ustudy.ui.viewmodels.NotesViewModel
import com.example.ustudy.util.Util
import com.example.ustudy.util.Util.NOTE_TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment() {
    private var _notesBinding: FragmentNotesBinding? = null
    private val notesBinding: FragmentNotesBinding
        get() = checkNotNull(_notesBinding) {
            Util.bindingErrorMessage("notes")
        }
    private val notesViewModel: NotesViewModel by activityViewModels()

    private val openNoteEditor: (note: Note) -> Unit = { note ->
        findNavController().navigate(
            R.id.createNote, args = bundleOf(NOTE_TAG to note)
        )
    }


    private val deleteNote: (note: Note) -> Unit = { note ->
        viewLifecycleOwner.lifecycleScope.launch {
            notesViewModel.deleteNote(note)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _notesBinding = FragmentNotesBinding.inflate(inflater, container, false)
        setUpFloatingButtonListener()
        notesBinding.rvNotes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        return notesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                notesViewModel.notes.collect { newNotes ->
                    notesBinding.rvNotes.adapter =
                        NotesAdapter(newNotes, openNoteEditor, deleteNote)
                }
            }
        }
    }

    private fun setUpFloatingButtonListener() {
        notesBinding.fabCreateNote.setOnClickListener {
            findNavController().navigate(
                R.id.createNote
            )
        }
    }


}
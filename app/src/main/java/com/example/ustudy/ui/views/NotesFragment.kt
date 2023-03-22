package com.example.ustudy.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ustudy.R
import com.example.ustudy.databinding.FragmentNotesBinding
import com.example.ustudy.util.Util

class NotesFragment : Fragment() {
    private var _notesBinding: FragmentNotesBinding? = null
    private val notesBinding: FragmentNotesBinding
        get() = checkNotNull(_notesBinding) {
            Util.bindingErrorMessage("notes")
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _notesBinding = FragmentNotesBinding.inflate(inflater, container, false)
        notesBinding.fabCreateNote.setOnClickListener {
            findNavController().navigate(
                R.id.createNote
            )
        }
        return notesBinding.root
    }


}
package com.example.ustudy.ui.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ustudy.databinding.FragmentNoteEditorBinding
import com.example.ustudy.util.Util

class NoteEditorFragment : Fragment() {
    private var _noteEditorBinding: FragmentNoteEditorBinding? = null
    private val noteEditorBinding: FragmentNoteEditorBinding
        get() = checkNotNull(_noteEditorBinding) {
            Util.bindingErrorMessage("notes-editor")
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _noteEditorBinding = FragmentNoteEditorBinding.inflate(inflater, container, false)
        noteEditorBinding.tiNoteContent.onFocusChangeListener =
            View.OnFocusChangeListener { _, isInFocus ->
                if (isInFocus)
                    noteEditorBinding.ivFinishedEditingIcon.visibility = View.VISIBLE
                else
                    Log.d("FOCUSABLE_EDITOR", "NO FOCUS")
                    noteEditorBinding.ivFinishedEditingIcon.visibility = View.GONE

            }
        return noteEditorBinding.root
    }
}
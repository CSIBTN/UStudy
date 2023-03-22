package com.example.ustudy.ui.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ustudy.data.local.Note
import com.example.ustudy.databinding.NoteItemBinding

class NotesAdapter(
    private val newNotes: List<Note>,
    private val onClickAction: (note: Note) -> Unit,
    private val onDeleteAction: (note: Note) -> Unit
) :
    RecyclerView.Adapter<NotesAdapter.NoteHolder>() {
    inner class NoteHolder(private val noteBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(noteBinding.root) {
        fun bind(note: Note) {
            noteBinding.noteTitle.text = note.title
            noteBinding.noteContent.text = note.content
            noteBinding.ivDeleteNote.setOnClickListener {
                onDeleteAction(note)
            }
            noteBinding.root.setOnClickListener {
                onClickAction(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater)
        return NoteHolder(binding)
    }


    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(newNotes[position])
    }

    override fun getItemCount(): Int = newNotes.size


}

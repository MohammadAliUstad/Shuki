package com.yugentech.shuki.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yugentech.shuki.data.notes.Note
import com.yugentech.shuki.data.room.Graph
import com.yugentech.shuki.repositories.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val notesRepository: NotesRepository = Graph.notesRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(value = emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note.asStateFlow()

    init {
        viewModelScope.launch {
            notesRepository.initializeDummyData()
            notesRepository.getAllNotes().collect { notesList ->
                _notes.value = notesList
            }
        }
    }

    fun getNote(id: Long): StateFlow<Note?> {
        viewModelScope.launch {
            notesRepository.getNote(id).collect { note ->
                _note.value = note
            }
        }
        return note
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteNote(note)
        }
    }
}
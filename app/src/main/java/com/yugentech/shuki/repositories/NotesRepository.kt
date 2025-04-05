package com.yugentech.shuki.repositories

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import com.yugentech.shuki.data.notes.Note
import com.yugentech.shuki.data.notes.NotesDao
import com.yugentech.shuki.data.utils.DummyData
import kotlinx.coroutines.flow.Flow

class NotesRepository(
    private val notesDao: NotesDao,
    application: Application
) {
    private val prefs = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val key = "isInitialized"

    suspend fun initializeDummyData() {
        if (!prefs.getBoolean(key, false)) {
            DummyData.dummyNotes.forEach { note ->
                notesDao.addNote(note)
            }
            prefs.edit { putBoolean(key, true) }
        }
    }

    suspend fun addNote(note: Note) {
        notesDao.addNote(note)
    }

    fun getNote(id: Long): Flow<Note> {
        return notesDao.getNote(id)
    }

    fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    suspend fun updateNote(note: Note) {
        notesDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note)
    }
}
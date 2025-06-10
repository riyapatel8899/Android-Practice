package com.example.room.room

import androidx.lifecycle.LiveData

class NoteRepository(private val notesDao: NoteDao) {
    val allNotes: LiveData<MutableList<Note>> = notesDao.getAllNote()

    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }

    suspend fun delete(note: Note) {
        notesDao.delete(note)
    }

    suspend fun update(note: Note) {
        notesDao.update(note)
    }

    fun deleteAllNotes() {
        notesDao.deleteAllNote()
    }

}
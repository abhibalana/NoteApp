package com.example.internproject

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {
    suspend fun insertNote(note:Note){
        noteDao.insertNote(note)
    }
    val allNotes :LiveData<List<Note>> = noteDao.getAllNotes()
}
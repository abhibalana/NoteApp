package com.example.internproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class noteViewModel(application: Application):AndroidViewModel(application) {
    private val repository:NoteRepository
    val allNotes : LiveData<List<Note>>
    init{
        val dao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }
    fun insertNote(note:Note)=viewModelScope.launch (Dispatchers.IO){
        repository.insertNote(note)
    }
    fun deleteNote(note:Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNot(note)
    }
}
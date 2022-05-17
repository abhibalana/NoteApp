package com.example.internproject

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note:Note)

    @Query("Select * from notes_table order BY id ASC")
    fun getAllNotes():LiveData<List<Note>>

     @Delete
     suspend fun deleteNote(note:Note)
}
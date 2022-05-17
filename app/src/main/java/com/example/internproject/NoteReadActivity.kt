package com.example.internproject

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_note_read.*

class NoteReadActivity : AppCompatActivity() {
    private lateinit var encryptPassword: encryptPassword
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_read)
        encryptPassword = encryptPassword()
        val title = intent.getStringExtra("title")!!
        val note = encryptPassword.decrypt(intent.getStringExtra("note")!!)
        if(note.isNotEmpty() && title.isNotEmpty()) {
            readmaintxt.text = title
            readNote.text = note
        }

    }
}
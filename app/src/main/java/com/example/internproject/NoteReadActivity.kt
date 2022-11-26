package com.example.internproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_note_read.*

class NoteReadActivity : AppCompatActivity() {
    private lateinit var encryptPassword: encryptPassword
    private lateinit var note :String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_read)
        encryptPassword = encryptPassword()
        val title = intent.getStringExtra("title")!!
        val en = intent.getBooleanExtra("encrypt",false)
        if(en) {
            note = encryptPassword.decrypt(intent.getStringExtra("note")!!)
        }
        else{
            note = intent.getStringExtra("note")!!
        }
            if(note.isNotEmpty() && title.isNotEmpty()) {
            readmaintxt.text = title
            readNote.text = note
        }
        shareButton.setOnClickListener {

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, readmaintxt.text.toString()+"\n"+readNote.text.toString())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "SharE Via")
            startActivity(shareIntent)

        }

    }
}
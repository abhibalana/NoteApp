package com.example.internproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_create_note.*
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : AppCompatActivity() {
    private lateinit var viewModel: noteViewModel
    private lateinit var encryptPassword: encryptPassword
    private lateinit var note1:String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        encryptPassword = encryptPassword()
                viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(noteViewModel::class.java)
        val id = intent.getStringExtra("userid").toString()
        submit1.setOnClickListener {
            val title = maintxt.text.toString()
            val note = Note.text.toString()
                 note1 = encryptPassword.encrypt(note)
            if(title.isNotEmpty()&&note1.isNotEmpty()){
                val note = Note(currentDate,title,note1,id.toInt())
                viewModel.insertNote(note)
                finish()
            }
            
        }
    }
}
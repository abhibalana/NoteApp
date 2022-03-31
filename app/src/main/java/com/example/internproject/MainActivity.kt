package com.example.internproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: noteViewModel
     var array :ArrayList<Note> = ArrayList()

    lateinit var id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NoteAdapter(this)
        recyclerView.adapter=adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(noteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { list ->
            if(fillArray(list)){
                adapter.updateList(array)

            }
        })

         id = intent.getStringExtra("userid").toString()

        submit.setOnClickListener {
            val text = maintxt.text.toString()
            if(text.isNotEmpty()){
                val note = Note(text,id.toInt())
                viewModel.insertNote(note)
            }
        }
    }

    private fun fillArray(list: List<Note>?): Boolean {
        array.clear()
        if(list!=null){
            list.indices.forEach { i ->
                if(list[i].userid.toString()==id){
                    array.add(list[i])
                }
            }
            return true
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.logout ->{
                deleteSharedPreferences("application")
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}







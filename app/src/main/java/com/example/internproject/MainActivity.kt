package com.example.internproject

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), noteRVAdapter {
    lateinit var viewModel: noteViewModel
     var array :ArrayList<Note> = ArrayList()




    lateinit var id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NoteAdapter(this,this)
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
            val intent = Intent(this,CreateNoteActivity::class.java)
            intent.putExtra("userid",id)
            startActivity(intent)
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
            R.id.newsPaper ->{
                openNewsPaper()
                return true
            }
            R.id.about ->{
                openAbout()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openAbout(){
        startActivity(Intent(this,AboutActivity::class.java))
    }

    private fun openNewsPaper() {
      val intent=  Intent(this,NewsActivity::class.java)
        intent.putExtra("userid",id)
  startActivity(intent)
    }

    override fun onItemClicked(note: Note) {
        showdialog("Enter Email to delete", note, false)


    }

    override fun onNoteClick(note: Note){
        if(note.encrypt) {
            showdialog("Enter Email to decrypt message",note,true)
        }
        else{
            val intent = Intent(this, NoteReadActivity::class.java)
            intent.putExtra("title", note.title)
            intent.putExtra("note", note.Note)
            intent.putExtra("encrypt",note.encrypt)
            startActivity(intent)
        }



    }
    fun showdialog(titlee:String,note:Note,f:Boolean){

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(titlee)

        val input = EditText(this)
        input.setHint("Enter Email")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

            val m_Text = input.text.toString()
            val email = getDatafromSharedPrefrence()
            if(email==m_Text && !f) {
              viewModel.deleteNote(note)
            }
            else if(email==m_Text&&f){
                val intent = Intent(this, NoteReadActivity::class.java)
                intent.putExtra("title", note.title)
                intent.putExtra("note", note.Note)
                intent.putExtra("encrypt",note.encrypt)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Enter Correct Email",Toast.LENGTH_SHORT).show()
            }
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()

    }

    fun getDatafromSharedPrefrence():String{
        val sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", "")!!
    }
}







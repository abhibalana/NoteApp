package com.example.internproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*

class LoginActivity : AppCompatActivity() {
    lateinit var viewModel: UserViewModel
    lateinit var array : List<User>
   lateinit var pref :  SharedPreferences
    var id:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()
        pref = getSharedPreferences("application",Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(UserViewModel::class.java)
        viewModel.allUser.observe(this, Observer {
          array=it
        })
        if(containsEmail()){
            val id = pref.getString("id",null)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userid", id)
                startActivity(intent)
                finish()
        }
        loginButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if(email.isNotEmpty()&&password.isNotEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()){
             if(checkIt(email,password)){
                 addSharedPreference(email,id)
                 val intent = Intent(this, MainActivity::class.java)
                 intent.putExtra("userid",id)
                 startActivity(intent)
                 finish()
             }
                else{
                    Toast.makeText(this,"Check credentials",Toast.LENGTH_SHORT).show()
             }
            }
        }
        signin.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }


    private fun checkIt(email: String, password: String): Boolean {
        for(i in array.indices){
            if(email==array[i].email && password==array[i].password){
                  id = array[i].user_id.toString()
                return true
            }

        }
        return false
    }
    @SuppressLint("CommitPrefEdits")
    fun addSharedPreference(email: String,id:String){
        val editor = pref.edit()
        editor.putString("email",email)
        editor.putString("id",id)
        editor.apply()


    }

    fun containsEmail():Boolean{
        val email = pref.getString("email",null)
        if(email!=null){
            return true
        }

        return false

    }

}
package com.example.internproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    lateinit var viewModel: UserViewModel
    lateinit var array : List<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar!!.hide()
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(UserViewModel::class.java)
        viewModel.allUser.observe(this, Observer {
             array=it
        })
     signupButton.setOnClickListener {
         val name = name.text.toString()
         val phnNumber = number.text.toString()
         val email = emailSignup.text.toString()
         val password = passwordSignup.text.toString()
         if(name.isNotEmpty()&&phnNumber.isNotEmpty()&&phnNumber.length==10&&email.isNotEmpty()&&password.isNotEmpty()){
             if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                 if(!checkuser(email)  ) {
                     val user = User(name, phnNumber, email, password)
                     viewModel.insertUser(user)
                     startActivity(Intent(this, LoginActivity::class.java))
                     Toast.makeText(this,"Successfully Register",Toast.LENGTH_SHORT).show()
                 }
                 else{
                     Toast.makeText(this,"Email exist",Toast.LENGTH_SHORT).show()
                 }
             }
             else{
                 Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
             }
         }
     }
    }

    private fun checkuser(email: String): Boolean {
        for(i in array.indices){
            if(array[i].email==email){
                return true
            }
        }
        return false

    }
}
package com.example.internproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {
    private val repository:UserRepository
    val allUser : LiveData<List<User>>
    init{
        val dao = UserDatabase.getDatabase(application).userDao()
         repository = UserRepository(dao)
        allUser = repository.allusers
    }
    fun insertUser(user:User)=viewModelScope.launch (Dispatchers.IO){
        repository.insertUser(user)
    }
}
package com.example.internproject

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val allusers :LiveData<List<User>> = userDao.findUser()
    suspend fun insertUser(user:User){
        userDao.insertUser(user)

    }
}
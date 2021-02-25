package com.example.myapp.models

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) { //recibe como parametro el dao
    val readAll: LiveData<List<User>> = userDao.readAll()
    suspend fun addUser(user:User){
        userDao.addUser(user)
    }

}
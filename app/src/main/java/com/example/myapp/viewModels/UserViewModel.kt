package com.example.myapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp.models.User
import com.example.myapp.models.UserDatabase
import com.example.myapp.models.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) { //se pasa el contexto de la aplicacion
    val readAll: LiveData<List<User>>
    private val repository: UserRepository

    init{
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAll = repository.readAll
    }

    fun addUser(user:User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }
}
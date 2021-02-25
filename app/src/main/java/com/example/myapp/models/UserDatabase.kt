package com.example.myapp.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class],version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object{
        @Volatile
        private var INSTANCE: UserDatabase ?= null
        fun getDatabase(context: Context):UserDatabase{ //funcion que devuelve un userdatabase
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){ //si la instancia no se ha creado
                    instance = Room.databaseBuilder( //creamos base de datos con el esquema especificado en la clase user
                        context.applicationContext,
                        UserDatabase::class.java,
                            "user_database"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}
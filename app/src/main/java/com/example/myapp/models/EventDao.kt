package com.example.myapp.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface EventDao {
    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun addEvent(evento: Evento)
}
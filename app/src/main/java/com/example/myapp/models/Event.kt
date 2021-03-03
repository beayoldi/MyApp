package com.example.myapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "event_table")
class Event (
        /*@PrimaryKey(autoGenerate = true)
        val id: Int,*/
        val tittle: String,
        val description: String,
        val capacity: Int,
        val date: String,
        val type: String,
        val priv: String//Boolean
        )



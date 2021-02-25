package com.example.myapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_table")
class Event (
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val tittle: String,
        val description: String,
        val capacity: Int
        )



package com.example.myapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "event_table")

data class Event(val tittle: String?=null,
                 val description: String?=null,
                 val capacity: Int?=null,
                 val date: String?=null,
                 val type: String?=null,
                 val priv: Boolean?=null) {
}



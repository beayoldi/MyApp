package com.example.myapp.models


import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

//@Entity(tableName = "event_table")
@Parcelize
data class Evento(val tittle: String?=null,
                  val description: String?=null,
                  val capacity: Int?=null,
                  val location: String?=null,
                  val date: String?=null,
                  val type: String?=null,
                  val priv: Boolean?=null,
                  var id: String?=null) :Parcelable





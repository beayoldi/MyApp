package com.example.myapp.models

interface MyCallback {
    fun onCallback(value:List<String>)
    fun onCallbackEvent(value:List<Evento>)
}
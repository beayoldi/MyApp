package com.example.myapp.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.myapp.R
import com.example.myapp.databinding.FragmentDetailEventBinding
import com.example.myapp.databinding.FragmentHomeBinding
import com.example.myapp.models.Evento
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class detail_event : Fragment() {

    private lateinit var binding: FragmentDetailEventBinding
    private lateinit var database: FirebaseDatabase
    private val args by navArgs<detail_eventArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailEventBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()
        binding.nombre.text = "${args.evento.tittle}"
        binding.aforo.text = "${args.evento.capacity}"
        binding.descripcion.text = "${args.evento.description}"
        binding.fecha.text = "${args.evento.date}"
        binding.ubicacion.text = "${args.evento.location}"
        Log.i("firebase", "Got value ${args.evento.id}")

        var email: String
        val user = Firebase.auth.currentUser


        binding.joinButton.setOnClickListener {
            user?.let {
                email = user.email.toString()
                if (email != null) {
                    reduceCounter(email.split('@')[0])
                }
            }
        }
        return binding.root


    }
    fun reduceCounter(user: String){
        val ref = database.getReference("users/"+user)
        var count = 0
        ref.child("ev_count").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            ref.child("ev_count").setValue(count)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }



}
package com.example.myapp.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapp.R
import com.example.myapp.databinding.FragmentDetailEventBinding
import com.example.myapp.databinding.FragmentHomeBinding
import com.example.myapp.models.Evento
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class detail_event : Fragment() {

    private lateinit var binding: FragmentDetailEventBinding
    private lateinit var database: FirebaseDatabase
    private val args by navArgs<detail_eventArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailEventBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()
        val user = Firebase.auth.currentUser

        binding.nombre.text = "${args.evento.tittle}"
        binding.aforo.text = "${args.evento.capacity}"
        binding.descripcion.text = "${args.evento.description}"
        binding.fecha.text = "${args.evento.date}"
        binding.ubicacion.text = "${args.evento.location}"
        Log.i("firebase", "Got value ${args.evento.id}")
        val id=args.evento.id
        var email: String

        binding.joinButton.setOnClickListener {
            user?.let {
                email = user.email.toString()
                val recipient = user.email.toString()
                val message= "Te has unido al evento llamado: "+args.evento.tittle+" /n Fecha del evento: "+args.evento.date
                val subject = "Nuevo Evento"
                sendEmail(recipient,subject,message)
                if (email != null) {
                    if (id != null) {
                        reduceCounter(email.split('@')[0],id)
                    }
                }
            }
            //Enviar correo

            findNavController().navigate(R.id.action_detail_event_to_homeFragment)
        }
        return binding.root


    }
    fun reduceCounter(user: String,id: String){
        val ref = database.getReference("users/"+user+"/eventos/"+id)
        var count = 0
        ref.child("capacity").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            var aux=it.value.toString().toInt()
            aux = aux-1
            ref.child("capacity").setValue(aux)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
    fun sendEmail(re:String, sub:String, men:String){
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data= Uri.parse("mailto:")
        mIntent.type="text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(re))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, arrayOf(sub))
        mIntent.putExtra(Intent.EXTRA_TEXT, arrayOf(men))
        try{
            startActivity(Intent.createChooser(mIntent,"Choose email"))
        }catch (e:Exception){
            //si algo sale mal
            Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()

        }

    }



}
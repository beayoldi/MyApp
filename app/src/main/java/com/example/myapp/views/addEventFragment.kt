package com.example.myapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentAddEventBinding
import com.example.myapp.models.Event
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class addEventFragment : Fragment() {
    private lateinit var binding: FragmentAddEventBinding
    private lateinit var database: FirebaseDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()
        val name = binding.eventName.text.toString()
        val desc = binding.descText.text.toString()
        val capacity = binding.capacity.text.toString()
        Log.d("MyTag",capacity.toString())
        val type = binding.type.text.toString()
        var priv = false
        val date = binding.date.text.toString()
        val user = Firebase.auth.currentUser
        var email: String
        binding.privado.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                priv = true
                Log.d("BB",buttonView.text.toString())
            } else {
                // The switch is disabled
                priv = false
            }
        }
        val event= Event(name, desc, Integer.parseInt(capacity), date, type, priv)
        binding.createEventbutton.setOnClickListener {
            user?.let {
                email = user.email.toString()
                if (email != null) {
                    writeEvent(email.split('@')[0],event)
                }
            }
            findNavController().navigate(R.id.action_addEventFragment_to_homeFragment)


        }

        return binding.root
    }

    fun writeEvent(user: String, evento: Event){
        val ref = database.getReference("users/"+user)
        var count = 0
        ref.child("ev_count").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            count =  it.value.toString().toInt()
            count+=1
            database.getReference("users/"+user+"/eventos/evento"+count.toString()).setValue(evento)
            ref.child("ev_count").setValue(count)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }


    }

}
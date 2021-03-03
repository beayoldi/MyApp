package com.example.myapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapp.databinding.FragmentAddEventBinding
import com.example.myapp.models.Event
import com.google.firebase.database.DatabaseReference
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


        binding.createEventbutton.setOnClickListener {
            database = FirebaseDatabase.getInstance()

            val name = binding.eventName.text.toString()
            val desc = binding.descText.text.toString()
            val capacity = binding.capacity.text.toString()
            Log.d("MyTag",capacity.toString())
            val type = binding.type.text.toString()
            binding.privado.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    // The switch is enabled/checked
                    buttonView.text = "Switch on"
                } else {
                    // The switch is disabled
                    buttonView.text = "Switch off"
                }
            }
            val priv = binding.privado.text.toString()
            val date = binding.date.text.toString()
            val event= Event(name, desc, Integer.parseInt(capacity), date, type, priv)
            writeEvent("beayoldi",event)
        }

        return binding.root
    }
    fun writeEvent(user: String, evento: Event){
        database.getReference("users/"+user).setValue(evento)

    }

}
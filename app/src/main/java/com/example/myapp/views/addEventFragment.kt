package com.example.myapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapp.databinding.Fragment1Binding
import com.example.myapp.databinding.FragmentAddEventBinding
import com.google.firebase.database.FirebaseDatabase


class addEventFragment : Fragment() {
    private lateinit var binding: FragmentAddEventBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        val database = FirebaseDatabase.getInstance()
        binding.createEventbutton.setOnClickListener {
            val name = binding.eventName.text.toString()
            val desc = binding.descText.text.toString()
            val capacity = binding.capacity.text
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
            val priv = binding.privado
            val date = binding.date.text
            val myRef = database.getReference("message")
            myRef.setValue(name)
        }

        return binding.root
    }

}
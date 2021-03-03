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
            val myRef = database.getReference("message")
            myRef.setValue("Hello, World!")
        }

        return binding.root
    }

}
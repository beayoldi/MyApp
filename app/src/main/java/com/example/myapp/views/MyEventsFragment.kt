package com.example.myapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentMyEventsBinding
import com.google.firebase.database.FirebaseDatabase


class MyEventsFragment : Fragment() {
    private lateinit var binding: FragmentMyEventsBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyEventsBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myEvNav.setOnNavigationItemSelectedListener { it ->
            when(it.itemId){
                R.id.home_ic -> findNavController().navigate(R.id.action_myEventsFragment_to_homeFragment)

            }
            true
        }
    }

}
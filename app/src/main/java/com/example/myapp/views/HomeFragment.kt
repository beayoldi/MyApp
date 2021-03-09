package com.example.myapp.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()
        //Para cambiar de fragment con el bottom nav



        val user = Firebase.auth.currentUser
        val email: String
        user?.let {
            email = user.email.toString()
            if (email != null) {
                val ref=database.getReference("users/"+email.split('@')[0])
                ref.child("ev_count").get().addOnSuccessListener {
                    if(it.value==null){
                        ref.child("ev_count").setValue(0)
                    }
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
            }
        }
        val ref = database.getReference()

        binding.addEvent.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addEventFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeNav.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.my_ev -> findNavController().navigate(R.id.action_homeFragment_to_myEventsFragment)

            }
        }
    }



}
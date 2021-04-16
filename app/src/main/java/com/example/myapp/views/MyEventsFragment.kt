package com.example.myapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.databinding.FragmentMyEventsBinding
import com.example.myapp.models.Evento
import com.example.myapp.viewModels.EventViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class MyEventsFragment() : Fragment() {
    private lateinit var binding: FragmentMyEventsBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyEventsBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()

        binding.addMyEvent.setOnClickListener {
            findNavController().navigate(R.id.action_myEventsFragment_to_addEventFragment)
        }

        readUsers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myEvNav.selectedItemId=R.id.my_ev
        binding.myEvNav.setOnNavigationItemSelectedListener { it ->
            when(it.itemId){
                R.id.home_ic -> findNavController().navigate(R.id.action_myEventsFragment_to_homeFragment)
            }
            true
        }
    }

    fun readUsers(){
        val adapter =ListAdapterMyEv()
        val recyclerView=binding.myEvList
        recyclerView.adapter=adapter
        recyclerView.layoutManager= LinearLayoutManager(requireContext())

        val user = Firebase.auth.currentUser
        var correo: String

        var evento: Evento? = null  // declare user object outside onCreate Method

        var eventList = mutableListOf<Evento>()

        user?.let {
            correo = user.email.toString().split('@')[0]
            val refe = database.getReference("users/$correo/eventos")
            val menuListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach {
                        evento = it.getValue(Evento::class.java)
                        evento?.let { eventList.add(evento!!) }
                    }
                    adapter.setData(eventList)
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // handle error
                }
            }
            refe.addListenerForSingleValueEvent(menuListener)
        }

    }

}
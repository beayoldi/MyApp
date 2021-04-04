package com.example.myapp.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.example.myapp.models.Evento
import com.google.firebase.database.DatabaseError


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()

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
        readUsers()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.my_ev -> findNavController().navigate(R.id.action_homeFragment_to_myEventsFragment)

            }
            true
        }
    }
    fun readUsers(): List<String>{
        val adapter =ListAdapter()
        val recyclerView=binding.eventList
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        var correo=""
        var correoList = mutableListOf<String>()
        val refe_correo=database.getReference("users/")
        var evento: Evento? = null  // declare user object outside onCreate Method

        var eventList = mutableListOf<Evento>()


        val correoListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach{
                    correo=it.key.toString()
                    correoList.add(correo)

                }

                for(item in correoList) {

                    val refe = database.getReference("users/" + item+ "/eventos")
                    val menuListener = object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            dataSnapshot.children.forEach{
                                evento = it.getValue(Evento::class.java)
                                if(evento?.priv ==false){
                                    Log.i("Hola caracola", "Got value ${it.key}")
                                    //it.key nos da el nombre del evento en la base de datos de firebase como por ej evento18
                                    evento?.let { eventList.add(evento!!) }
                                }

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
            override fun onCancelled(databaseError: DatabaseError) {

                // handle error
            }
        }

        refe_correo.addListenerForSingleValueEvent(correoListener)
        return correoList
    }



}
package com.example.myapp.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentAddEventBinding
import com.example.myapp.models.Evento
import com.example.myapp.viewModels.EventViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*


class addEventFragment : Fragment() {
    private lateinit var binding: FragmentAddEventBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var eventViewModel: EventViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        database = FirebaseDatabase.getInstance()
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        binding.date.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                binding.dateTxt.text = ""+dayOfMonth+"/"+(month+1)+"/"+year
            }, year, month, day)
            dpd.show()
        }
        var priv = false
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
        binding.createEventbutton.setOnClickListener {
            val name = binding.eventName.text.toString()
            val desc = binding.descText.text.toString()
            val capacity = binding.capacity.text.toString()
            val type = binding.type.text.toString()
            val location = binding.eventLoc.text.toString()
            val date = binding.dateTxt.text.toString()

            val user = Firebase.auth.currentUser
            var email: String
            val pair = eventViewModel.encryptData(location.toString())
            val encodedIV: String = Base64.encodeToString(pair.first, Base64.DEFAULT)
            val encodedLoc: String = Base64.encodeToString(pair.second, Base64.DEFAULT)

            val event= Evento(name, desc, Integer.parseInt(capacity), location,date, type, priv,encodedIV,encodedLoc)
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

    fun writeEvent(user: String, evento: Evento){
        val ref = database.getReference("users/"+user)
        var count = 0
        ref.child("ev_count").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            count =  it.value.toString().toInt()
            count+=1
            //val event= Evento(evento.tittle, evento.description, Integer.parseInt(evcapacity), location,date, type, priv)

            database.getReference("users/"+user+"/eventos/evento"+count.toString()).setValue(evento)
            ref.child("ev_count").setValue(count)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }


    }



}
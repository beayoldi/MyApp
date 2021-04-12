package com.example.myapp.views

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapp.R
import com.example.myapp.databinding.FragmentDetailEventBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*


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
        val date = args.evento.date.toString().split("/")
        binding.joinButton.setOnClickListener {
            user?.let {
                email = user.email.toString()
                val recipient = user.email.toString()
                val message= "Te has unido al evento llamado: "+args.evento.tittle+" /n Fecha del evento: "+args.evento.date
                val subject = "Nuevo Evento"

                sendEmail(date[2].toInt(), date[1].toInt(), date[0].toInt(), args.evento.tittle.toString(), args.evento.description.toString())
                if (email != null) {
                    if (id != null) {
                        reduceCounter(email.split('@')[0], id)
                    }
                }
            }
            //Enviar correo

            findNavController().navigate(R.id.action_detail_event_to_homeFragment)
        }
        return binding.root


    }
    fun reduceCounter(user: String, id: String){
        val ref = database.getReference("users/" + user + "/eventos/" + id)
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
    fun sendEmail(year: Int, mnth: Int, day: Int, title: String, desc: String){
        /*
        val calID: Long = 3
        val startMillis: Long = Calendar.getInstance().run {
            set(year, (mnth-1), day, 0,0)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(year, (mnth-1), day, 0,0)
            timeInMillis
        }
        val cr: ContentResolver = requireContext().getContentResolver()

        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, startMillis)
            put(CalendarContract.Events.DTEND, endMillis)
            put(CalendarContract.Events.TITLE, title)
            put(CalendarContract.Events.DESCRIPTION, desc)
            put(CalendarContract.Events.CALENDAR_ID, calID)
            put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles")
        }
        val uri: Uri? = cr.insert(CalendarContract.Events.CONTENT_URI, values)
        */


        val beginCal = Calendar.getInstance()
        beginCal[year, (mnth-1), day, 0] = 0

        val endCal = Calendar.getInstance()
        endCal[year, (mnth-1), day, 0] = 0
        val intent = Intent(Intent.ACTION_INSERT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(CalendarContract.Events.TITLE, title)
        intent.putExtra(CalendarContract.Events.DESCRIPTION, desc)
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "")
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginCal.timeInMillis)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endCal.timeInMillis)
        intent.putExtra(CalendarContract.Events.ALL_DAY, 0)
        intent.putExtra(CalendarContract.Events.STATUS, 1)
        intent.putExtra(CalendarContract.Events.VISIBLE, 0)
        intent.putExtra(CalendarContract.Events.HAS_ALARM, 1)
        startActivity(intent)




    }



}
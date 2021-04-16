package com.example.myapp.views


import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapp.R
import com.example.myapp.databinding.FragmentDetailEventBinding
import com.example.myapp.viewModels.EventViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*


class detail_event() : Fragment() {

    private lateinit var binding: FragmentDetailEventBinding
    private lateinit var database: FirebaseDatabase
    private val args by navArgs<detail_eventArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailEventBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()


        binding.nombre.text = "${args.evento.tittle}"
        binding.aforo.text = "${args.evento.capacity}"
        binding.descripcion.text = "${args.evento.description}"
        binding.fecha.text =  "${args.evento.date}"
        binding.ubicacion.text = "${args.evento.location}"
        val user = "${args.evento.user}"
        Log.i("firebase", "Got value ${args.evento.id}")
        val id=args.evento.id
        val date = args.evento.date.toString().split("/")
        binding.joinButton.setOnClickListener {
            if (id != null) {
                reduceCounter(user, id)
            }
            addCalendar(date[2].toInt(), date[1].toInt(), date[0].toInt(), args.evento.tittle.toString(), args.evento.description.toString())

            //Enviar correo

            findNavController().navigate(R.id.action_detail_event_to_homeFragment)
        }
        return binding.root


    }
    fun reduceCounter(user: String, id: String){
        val ref = database.getReference("users/" + user + "/eventos/" + id)
        Log.i("SEEEEEEEEEEEEEEEEEEEE", "Got value ${ref.toString()}")
        ref.child("capacity").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            var aux=it.value.toString().toInt()
            aux = aux-1
            ref.child("capacity").setValue(aux)
            Log.i("OOO", "Got value ${aux}")
            Log.i("VAAAAAA", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
    fun addCalendar(year: Int, mnth: Int, day: Int, title: String, desc: String){

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
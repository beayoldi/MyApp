package com.example.myapp.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.FragmentEventListHomeBinding
import com.example.myapp.models.Evento

class ListAdapterMyEv(): RecyclerView.Adapter<ListAdapterMyEv.MyViewHolder>() {

    private var event_list = emptyList<Evento>()

    class MyViewHolder(val context: Context, val binding: FragmentEventListHomeBinding, eventList: List<Evento>): RecyclerView.ViewHolder(binding.root){
        init {
            var navController: NavController? = null
            binding.nombreEvento.setOnClickListener{
                val position: Int = adapterPosition
                val event:Evento = eventList[position]
                navController = Navigation.findNavController(itemView)
                val action = MyEventsFragmentDirections.actionMyEventsFragmentToDetailEvent(event)
                navController!!.navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FragmentEventListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(parent.context, binding, event_list)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =event_list[position]
        with (holder) {
            binding.nombreEvento.text =currentItem.tittle.toString()
            binding.aforoEvento.text = currentItem.capacity.toString()
            binding.fechaEvento.text = currentItem.date.toString()
            binding.locEvento.text = currentItem.location.toString()
        }
    }

    override fun getItemCount(): Int {
        return event_list.size
    }

    fun setData (evento_list: List<Evento>){
        this.event_list=evento_list
        notifyDataSetChanged()
    }
}
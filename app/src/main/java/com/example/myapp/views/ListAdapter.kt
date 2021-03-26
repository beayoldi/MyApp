package com.example.myapp.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.EventListHomeBinding
import com.example.myapp.models.Evento

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var event_list = emptyList<Evento>()

    class MyViewHolder(val context: Context, val binding: EventListHomeBinding): RecyclerView.ViewHolder(binding.root){
        /*init {
            binding.nombreEvento.setOnClickListener{
                val action: NavDirections =
                findNavController().navigate()
            }
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =event_list[position]
        with (holder) {
            binding.nombreEvento.text =currentItem.tittle.toString()
            binding.aforoEvento.text = currentItem.capacity.toString()
            binding.fechaEvento.text = currentItem.date.toString()
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
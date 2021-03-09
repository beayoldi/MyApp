package com.example.myapp.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.EventListHomeBinding
import com.example.myapp.models.Event

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var event_list = emptyList<Event>()

    class MyViewHolder(val binding: EventListHomeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =event_list[position]
        with (holder) {
            binding.nombreEvento.text = currentItem.tittle.toString()
            binding.aforoEvento.text = currentItem.capacity.toString()
            binding.fechaEvento.text = currentItem.date.toString()
        }
    }

    override fun getItemCount(): Int {
        return event_list.size
    }

    fun setData (event_list: List<Event>){
        this.event_list=event_list
        notifyDataSetChanged()
    }
}
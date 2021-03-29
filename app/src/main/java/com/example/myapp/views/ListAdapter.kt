package com.example.myapp.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.databinding.EventListHomeBinding
import com.example.myapp.models.Evento

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var event_list = emptyList<Evento>()

    class MyViewHolder(val context: Context, val binding: EventListHomeBinding, eventList: List<Evento>): RecyclerView.ViewHolder(binding.root){
        init {
            var navController: NavController? = null
            binding.nombreEvento.setOnClickListener{
                val position: Int = adapterPosition
                //val evento = eventList[position]
                navController = Navigation.findNavController(itemView)
                navController!!.navigate(R.id.event_list)
                navController!!.navigate(position)
                navController!!.navigate(R.id.action_homeFragment_to_detail_event)

                //val action = HomeFragmentDirections.actionHomeFragmentToDetailEvent(evento)
                //Toast.makeText(itemView.context, "You clicked on item ${position +1}", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(parent.context, binding, event_list)
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
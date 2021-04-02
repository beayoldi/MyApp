package com.example.myapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.myapp.R
import com.example.myapp.databinding.FragmentDetailEventBinding
import com.example.myapp.databinding.FragmentHomeBinding


class detail_event : Fragment() {

    private lateinit var binding: FragmentDetailEventBinding
    private val args by navArgs<detail_eventArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailEventBinding.inflate(inflater, container, false)
        binding.nombre.text = "${args.evento.tittle}"
        return binding.root


    }


}
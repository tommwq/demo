package com.tq.navigationdemo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tq.navigationdemo.databinding.FragmentMain2Binding

class MainFragment2 : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = com.tq.navigationdemo.databinding.FragmentMain2Binding.inflate(inflater, container, false)
        return binding.root
    }
}

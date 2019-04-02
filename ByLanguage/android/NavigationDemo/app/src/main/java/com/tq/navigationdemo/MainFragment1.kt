package com.tq.navigationdemo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tq.navigationdemo.databinding.FragmentMain1Binding

class MainFragment1 : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMain1Binding.inflate(inflater, container, false)
        return binding.root
    }
}

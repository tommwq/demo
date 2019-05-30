package com.tq.startup.tabbedactivity

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tq.startup.tabbedactivity.databinding.FragmentTab2Binding
import com.tq.startup.tabbedactivity.viewmodel.Tab2ViewModel


class Tab2Fragment : Fragment() {

    companion object {
        fun newInstance() = Tab2Fragment()
    }

    private lateinit var viewModel: Tab2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTab2Binding.inflate(inflater, container, false)
        setupUi(binding)
        return binding.root
    }

    private fun setupUi(binding: FragmentTab2Binding) {
        val viewModel = ViewModelProviders.of(this).get(Tab2ViewModel::class.java)
        binding.viewModel = viewModel
    }
}

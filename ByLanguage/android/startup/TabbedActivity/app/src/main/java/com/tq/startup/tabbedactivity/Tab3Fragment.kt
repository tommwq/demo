package com.tq.startup.tabbedactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tq.startup.tabbedactivity.databinding.FragmentTab2Binding
import com.tq.startup.tabbedactivity.databinding.FragmentTab3Binding
import com.tq.startup.tabbedactivity.viewmodel.Tab3ViewModel


class Tab3Fragment : Fragment() {

    companion object {
        fun newInstance() = Tab3Fragment()
    }

    private lateinit var viewModel: Tab3ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTab3Binding.inflate(inflater, container, false)
        setupUi(binding)
        return binding.root
    }

    private fun setupUi(binding: FragmentTab3Binding) {
        val viewModel = ViewModelProviders.of(this).get(Tab3ViewModel::class.java)
        binding.viewModel = viewModel
    }
}

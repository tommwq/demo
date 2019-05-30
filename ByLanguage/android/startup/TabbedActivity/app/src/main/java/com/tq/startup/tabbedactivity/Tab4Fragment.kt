package com.tq.startup.tabbedactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tq.startup.tabbedactivity.databinding.FragmentTab2Binding
import com.tq.startup.tabbedactivity.databinding.FragmentTab4Binding
import com.tq.startup.tabbedactivity.viewmodel.Tab4ViewModel


class Tab4Fragment : Fragment() {

    companion object {
        fun newInstance() = Tab4Fragment()
    }

    private lateinit var viewModel: Tab4ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTab4Binding.inflate(inflater, container, false)
        setupUi(binding)
        return binding.root
    }

    private fun setupUi(binding: FragmentTab4Binding) {
        val viewModel = ViewModelProviders.of(this).get(Tab4ViewModel::class.java)
        binding.viewModel = viewModel
    }
}

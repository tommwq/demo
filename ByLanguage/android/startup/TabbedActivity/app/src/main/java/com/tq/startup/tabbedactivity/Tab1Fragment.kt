package com.tq.startup.tabbedactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tq.startup.tabbedactivity.adapter.QuotationAdapter
import com.tq.startup.tabbedactivity.data.AppDatabase
import com.tq.startup.tabbedactivity.data.QuotationRepository
import com.tq.startup.tabbedactivity.databinding.FragmentTab1Binding
import com.tq.startup.tabbedactivity.viewmodel.Tab1ViewModel
import com.tq.startup.tabbedactivity.viewmodel.Tab1ViewModelFactory
import kotlinx.android.synthetic.main.fragment_tab1.*

/**
 * A placeholder fragment containing a simple view.
 */
class Tab1Fragment : Fragment() {

    private lateinit var tab1ViewModel: Tab1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTab1Binding.inflate(inflater, container, false)
        setupUi(binding)
        return binding.root
    }

    private fun setupUi(binding: FragmentTab1Binding) {
        val factory = Tab1ViewModelFactory(QuotationRepository.getInstance(AppDatabase.getInstance(context!!).quotationDao()))

        val viewModel = ViewModelProviders.of(this, factory).get(Tab1ViewModel::class.java)
        binding.viewModel = viewModel

        val adapter = QuotationAdapter()
        binding.quotations.adapter = adapter

        viewModel.quotations.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}
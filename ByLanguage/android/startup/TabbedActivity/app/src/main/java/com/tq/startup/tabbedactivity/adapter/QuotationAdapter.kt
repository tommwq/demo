package com.tq.startup.tabbedactivity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tq.startup.tabbedactivity.data.Quotation
import com.tq.startup.tabbedactivity.databinding.ItemQuotationBinding
import com.tq.startup.tabbedactivity.viewmodel.QuotationViewModel

class QuotationAdapter : ListAdapter<Quotation, QuotationViewHolder>(QuotationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotationViewHolder =
        QuotationViewHolder(
            ItemQuotationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: QuotationViewHolder, position: Int) {
        val quotation = getItem(position)
        holder.apply {
            bind(quotation)
            itemView.tag = quotation
        }
    }
}

class QuotationViewHolder(private val binding: ItemQuotationBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Quotation) {
        binding.apply {
            viewModel = viewModel ?: QuotationViewModel()
            viewModel!!.author = item.author
            viewModel!!.content = item.content

            executePendingBindings()
        }
    }
}

class QuotationDiffCallback : DiffUtil.ItemCallback<Quotation>() {
    override fun areItemsTheSame(oldItem: Quotation, newItem: Quotation): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Quotation, newItem: Quotation): Boolean =
        (oldItem.author == newItem.author && oldItem.content == newItem.content)
}
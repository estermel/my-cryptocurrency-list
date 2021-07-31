package com.exercise.mycyprocurrency.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exercise.mycyprocurrency.data.CurrencyInfo
import com.exercise.mycyprocurrency.databinding.ItemCurrencyBinding

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private var currencies: List<CurrencyInfo>? = null

    fun submitData(currencies: List<CurrencyInfo>) {
        this.currencies = currencies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currencies?.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int = currencies?.size ?: 0

    inner class ViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: CurrencyInfo) {
            binding.tvCurrencyShort.text = currency.name[0].toString()
            binding.tvCurrencyName.text = currency.name
            binding.tvCurrencySymbol.text = currency.symbol
        }

    }

}
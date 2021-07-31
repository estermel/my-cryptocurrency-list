package com.exercise.mycyprocurrency.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.exercise.mycyprocurrency.R
import com.exercise.mycyprocurrency.data.CurrencyInfo
import com.exercise.mycyprocurrency.databinding.FragmentCurrencyListBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyListFragment : Fragment(R.layout.fragment_currency_list),
    SearchView.OnQueryTextListener {

    private val viewModel by viewModel<CurrencyViewModel>()
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var binding: FragmentCurrencyListBinding
    private var sortedCurrencies: MutableList<CurrencyInfo>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrencyListBinding.bind(view)
        setupAdapter()
        setCurrencies()
        binding.searchView.setOnQueryTextListener(this)
    }

    private fun isFromSortedCurrencies(arguments: Bundle): Boolean {
        return arguments.containsKey(BUNDLE_CURRENCIES)
    }

    private fun setSortedCurrencies(arguments: Bundle) {
        val bundle = arguments.getParcelableArrayList<CurrencyInfo>(BUNDLE_CURRENCIES)
        bundle?.let {
            sortedCurrencies = mutableListOf()
            with(sortedCurrencies) { this?.addAll(bundle) }
        }
    }

    private fun setCurrencies() {
        arguments?.let {
            if (isFromSortedCurrencies(it)) setSortedCurrencies(it)
            else loadCurrencies()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { getCurrenciesByName(query) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun setupAdapter() {
        currencyAdapter = CurrencyAdapter()
        binding.rvCurrency.apply {
            adapter = currencyAdapter
            layoutManager = LinearLayoutManager(this@CurrencyListFragment.context)
        }
    }

    private fun loadCurrencies() {
        Toast.makeText(this@CurrencyListFragment.context, "Loading currencies", Toast.LENGTH_SHORT)
            .show()

        viewModel.getAllCurrencies()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                currencyAdapter.submitData(it)
            }
    }

    private fun getCurrenciesByName(query: String) {
        Toast.makeText(this@CurrencyListFragment.context, "Searching currency", Toast.LENGTH_SHORT)
            .show()

        viewModel.getCurrenciesByName("%$query%")
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("currencies", "by name $query: $it")
                currencyAdapter.submitData(it)
                currencyAdapter.notifyDataSetChanged()
            }
    }

    companion object {
        private const val BUNDLE_CURRENCIES = "BUNDLE_CURRENCIES"

        fun newInstance(currencies: ArrayList<CurrencyInfo>): CurrencyListFragment {
            val fragment = CurrencyListFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(BUNDLE_CURRENCIES, currencies)
            fragment.arguments = bundle
            return fragment
        }
    }

}
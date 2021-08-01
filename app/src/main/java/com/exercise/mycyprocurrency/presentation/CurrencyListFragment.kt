package com.exercise.mycyprocurrency.presentation

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.exercise.mycyprocurrency.R
import com.exercise.mycyprocurrency.databinding.FragmentCurrencyListBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyListFragment : Fragment(R.layout.fragment_currency_list),
    SearchView.OnQueryTextListener, CurrencyAdapter.OnItemClickListener {

    private val viewModel by viewModel<CurrencyViewModel>()
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var binding: FragmentCurrencyListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrencyListBinding.bind(view)
        setupAdapter()
        getCurrencyList()
        binding.searchView.setOnQueryTextListener(this)
        currencyAdapter.setListener(this)
    }

    private fun getCurrencyList() {
        arguments?.let { args ->
            args.containsKey(BUNDLE_CURRENCIES).let {
                if (args.getString(BUNDLE_CURRENCIES).equals(KEY_ALL_SORTED_CURRENCIES))
                    loadSortedCurrencies()
                else
                    loadCurrencies()
            }
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
        viewModel.getAllCurrencies()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                hideError()
                currencyAdapter.submitData(it)
            }
    }

    private fun loadSortedCurrencies() {
        viewModel.getAllSortedCurrencies()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                hideError()
                currencyAdapter.submitData(it)
            }
    }

    private fun getCurrenciesByName(query: String) {
        viewModel.getCurrenciesByName("%$query%")
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isEmpty()) {
                    showError("Result for '$query' not found")
                } else {
                    hideError()
                    currencyAdapter.submitData(it)
                }
            }
    }

    private fun showError(message: String) {
        binding.rvCurrency.visibility = GONE
        binding.tvError.visibility = VISIBLE
        binding.tvError.text = message
    }

    private fun hideError() {
        binding.rvCurrency.visibility = VISIBLE
        binding.tvError.visibility = GONE
    }

    companion object {
        private const val BUNDLE_CURRENCIES = "BUNDLE_CURRENCIES"
        const val KEY_ALL_CURRENCIES = "KEY_ALL_CURRENCIES"
        const val KEY_ALL_SORTED_CURRENCIES = "KEY_ALL_SORTED_CURRENCIES"

        fun newInstance(actionKey: String): CurrencyListFragment {
            val fragment = CurrencyListFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_CURRENCIES, actionKey)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onItemClicked() {
        Toast.makeText(this@CurrencyListFragment.context, "Item clicked", Toast.LENGTH_SHORT).show()
    }

}
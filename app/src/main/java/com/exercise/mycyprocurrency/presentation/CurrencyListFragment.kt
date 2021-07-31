package com.exercise.mycyprocurrency.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.exercise.mycyprocurrency.data.CurrencyInfo
import com.exercise.mycyprocurrency.databinding.FragmentCurrencyListBinding
import io.reactivex.FlowableSubscriber
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.reactivestreams.Subscription

class CurrencyListFragment : FragmentActivity() {

    private val viewModel by viewModel<CurrencyViewModel>()
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var binding: FragmentCurrencyListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCurrencyListBinding.inflate(layoutInflater)
        setupAdapter()
        loadCurrencies()
    }

    private fun setupAdapter() {
        currencyAdapter = CurrencyAdapter()
        binding.rvCurrency.apply {
            adapter = currencyAdapter
            layoutManager = LinearLayoutManager(this@CurrencyListFragment)
        }
    }

    private fun loadCurrencies() {
        viewModel.getAllCurrencies()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : FlowableSubscriber<List<CurrencyInfo>> {
                override fun onSubscribe(s: Subscription) {}

                override fun onNext(currencies: List<CurrencyInfo>) {
                    Log.d("loadCurrencies onNext", currencies.toString())
                    currencyAdapter.submitData(currencies)
                }

                override fun onError(e: Throwable) {
                    Log.e("loadCurrencies onError", e.toString())
                }

                override fun onComplete() {
                    Log.d("loadCurrencies", "onComplete")
                    Toast.makeText(this@CurrencyListFragment, "Done loading", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    companion object {

        fun newInstance(): CurrencyListFragment = CurrencyListFragment()

    }

}
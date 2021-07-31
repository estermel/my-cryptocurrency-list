package com.exercise.mycyprocurrency.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.exercise.mycyprocurrency.R
import com.exercise.mycyprocurrency.data.CurrencyInfo
import com.exercise.mycyprocurrency.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel by viewModel<CurrencyViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnLoadData.setOnClickListener(this)
        binding.btnSortData.setOnClickListener(this)
        binding.btnInsertData.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLoadData -> {
                binding.gButtons.visibility = View.GONE
                beginCurrencyListFragment(CurrencyListFragment())
            }
            R.id.btnSortData -> {
                Toast.makeText(this, "Sorting currencies ASC...", Toast.LENGTH_SHORT).show()
                getAndSortCurrencies()
            }
            R.id.btnInsertData -> {
                insertInitialData()
                Toast.makeText(this, "Done inserting", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun beginCurrencyListFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, fragment)
            .commit()
    }

    private fun getAndSortCurrencies() {
        var sortedCurrencies = listOf<CurrencyInfo>()
        viewModel.getAllCurrencies()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                sortedCurrencies = it.sortedBy { it.name }
            }

        beginCurrencyListFragment(CurrencyListFragment.newInstance(sortedCurrencies as ArrayList<CurrencyInfo>))
    }

    private fun insertInitialData() {
        viewModel.insert(dummyCurrencies())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(this, "Done inserting", Toast.LENGTH_SHORT).show()
            }
    }

    private fun dummyCurrencies(): MutableList<CurrencyInfo> {
        val currencies = mutableListOf<CurrencyInfo>()
        currencies.add(CurrencyInfo("BTC", "Bitcoin", "BTC"))
        currencies.add(CurrencyInfo("ETH", "Ethereum", "ETH"))
        currencies.add(CurrencyInfo("XRP", "XRP", "XRP"))
        currencies.add(CurrencyInfo("BCH", "Bitcoin Cash", "BCH"))
        currencies.add(CurrencyInfo("LTC", "Litecoin", "LTC"))
        currencies.add(CurrencyInfo("EOS", "EOS", "EOS"))
        currencies.add(CurrencyInfo("BNB", "Binance Coin", "BNB"))
        currencies.add(CurrencyInfo("LINK", "Chainlink", "LINK"))
        currencies.add(CurrencyInfo("NEO", "NEO", "NEO"))
        currencies.add(CurrencyInfo("ETC", "Ethereum Classic", "ETC"))
        currencies.add(CurrencyInfo("ONT", "Ontology", "ONT"))
        currencies.add(CurrencyInfo("CRO", "Crypto.com Chain", "CRO"))
        currencies.add(CurrencyInfo("CUC", "Cucumber", "CUC"))
        currencies.add(CurrencyInfo("USDC", "USD Coin", "USDC"))

        return currencies
    }
}
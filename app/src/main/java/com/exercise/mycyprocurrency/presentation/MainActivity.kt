package com.exercise.mycyprocurrency.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exercise.mycyprocurrency.R
import com.exercise.mycyprocurrency.data.CurrencyInfo
import com.exercise.mycyprocurrency.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel by viewModel<CurrencyViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

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
                //TODO execute query selection from db, then display in fragment
            }
            R.id.btnSortData -> {
                //TODO sort list currency asc
            }
            R.id.btnInsertData -> {
                insertInitialData()
                Toast.makeText(this, "Done inserting", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertInitialData() {
        viewModel.insert(dummyCurrencies())
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
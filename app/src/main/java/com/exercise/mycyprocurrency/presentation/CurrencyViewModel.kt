package com.exercise.mycyprocurrency.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.exercise.mycyprocurrency.data.CurrencyDb
import com.exercise.mycyprocurrency.data.CurrencyInfo
import io.reactivex.Flowable

class CurrencyViewModel(
    private val db: CurrencyDb,
    application: Application
) : AndroidViewModel(application) {

    fun getAllCurrencies(): Flowable<List<CurrencyInfo>> {
        return db.currencyDao.getAllCurrencies()
    }

    fun getCurrenciesByName(name: String): Flowable<List<CurrencyInfo>> {
        return db.currencyDao.getCurrencyByName(name)
    }

    fun insert(currencies: MutableList<CurrencyInfo>) {
        return db.currencyDao.insert(currencies)
    }

}
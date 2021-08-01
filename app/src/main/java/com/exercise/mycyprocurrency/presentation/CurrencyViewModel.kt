package com.exercise.mycyprocurrency.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.exercise.mycyprocurrency.data.CurrencyDb
import com.exercise.mycyprocurrency.data.CurrencyInfo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CurrencyViewModel(
    private val db: CurrencyDb,
    application: Application
) : AndroidViewModel(application) {

    fun getAllCurrencies(): Flowable<List<CurrencyInfo>> {
        return db.currencyDao.getAllCurrencies()
    }

    fun getAllSortedCurrencies(): Flowable<List<CurrencyInfo>> {
        return db.currencyDao.getAllSortedCurrencies()
    }

    fun getCurrenciesByName(name: String): Flowable<List<CurrencyInfo>> {
        return db.currencyDao.getCurrencyByName(name)
    }

    fun insert(currencies: MutableList<CurrencyInfo>): Completable {
        return db.currencyDao.insert(currencies)
    }

    fun isCurrencyEmpty(): Boolean {
        var isEmpty = false
        getAllCurrencies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isEmpty = it.isEmpty()
            }

        return isEmpty
    }

}
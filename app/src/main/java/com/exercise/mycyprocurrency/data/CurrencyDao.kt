package com.exercise.mycyprocurrency.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencies: MutableList<CurrencyInfo>): Completable

    @Query("SELECT * FROM currency_info")
    fun getAllCurrencies(): Flowable<List<CurrencyInfo>>

    @Query("SELECT * FROM currency_info ORDER BY name ASC")
    fun getAllSortedCurrencies(): Flowable<List<CurrencyInfo>>

    @Query("SELECT * FROM currency_info WHERE name LIKE :name")
    fun getCurrencyByName(name: String): Flowable<List<CurrencyInfo>>

}
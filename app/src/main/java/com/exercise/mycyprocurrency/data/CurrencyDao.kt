package com.exercise.mycyprocurrency.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencies: MutableList<CurrencyInfo>)

    @Query("SELECT * FROM currency_info")
    fun getAllCurrencies() : List<CurrencyInfo>

    @Query("SELECT * FROM currency_info WHERE (name LIKE '%:name%')")
    fun getCurrencyByName(name: String): List<CurrencyInfo>

}
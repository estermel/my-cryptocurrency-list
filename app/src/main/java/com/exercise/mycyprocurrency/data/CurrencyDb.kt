package com.exercise.mycyprocurrency.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyInfo::class], version = 1, exportSchema = false)
abstract class CurrencyDb : RoomDatabase() {

    abstract val currencyDao: CurrencyDao

}
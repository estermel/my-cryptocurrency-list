package com.exercise.mycyprocurrency.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_info")
data class CurrencyInfo(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val symbol: String
)

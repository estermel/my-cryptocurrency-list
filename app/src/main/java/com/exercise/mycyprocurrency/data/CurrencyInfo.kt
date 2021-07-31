package com.exercise.mycyprocurrency.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "currency_info")
data class CurrencyInfo(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String,
    val symbol: String
) : Parcelable

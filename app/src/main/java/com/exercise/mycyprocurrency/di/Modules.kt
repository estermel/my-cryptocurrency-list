package com.exercise.mycyprocurrency.di

import android.app.Application
import androidx.room.Room
import com.exercise.mycyprocurrency.data.CurrencyDao
import com.exercise.mycyprocurrency.data.CurrencyDb
import com.exercise.mycyprocurrency.presentation.CurrencyViewModel
import org.koin.android.compat.ViewModelCompat.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dbModule = module {
    fun provideDatabase(application: Application): CurrencyDb {
        return Room.databaseBuilder(application, CurrencyDb::class.java, "currency.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCurrenciesDao(database: CurrencyDb): CurrencyDao {
        return  database.currencyDao
    }

    single { provideDatabase(get()) }
    single { provideCurrenciesDao(get()) }
}

val viewModelModule = module {
    viewModel {
        CurrencyViewModel(get(), get())
    }
}
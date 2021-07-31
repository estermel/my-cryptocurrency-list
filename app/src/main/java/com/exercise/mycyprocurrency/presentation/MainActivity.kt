package com.exercise.mycyprocurrency.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.exercise.mycyprocurrency.R
import com.exercise.mycyprocurrency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnLoadData.setOnClickListener(this)
        binding.btnSortData.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLoadData -> {
                //TODO execute query selection from db, then display in fragment
            }
            R.id.btnSortData -> {
                //TODO sort list currency asc
            }
        }
    }
}
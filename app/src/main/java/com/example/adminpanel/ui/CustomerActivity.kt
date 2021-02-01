package com.example.adminpanel.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpanel.databinding.ActivityCustomerBinding

class CustomerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
package com.example.adminpanel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adminpanel.R
import com.example.adminpanel.api.model.Customer
import com.example.adminpanel.data.CustomerViewModel
import com.example.adminpanel.databinding.FragmentNewCustomerBinding
import java.util.*

class NewCustomerFragment : Fragment() {

    private lateinit var binding: FragmentNewCustomerBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(CustomerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_new_customer, container, false)

        binding.buttonSaveCustomer.setOnClickListener {
            viewModel.addCustomer(
                Customer(
                    UUID.randomUUID().toString(),
                    binding.inputCustomerName.text.toString(),
                    binding.inputCustomerEmail.text.toString(),
                    "NO_PIC"
                )
            )
        }
        return binding.root
    }
}
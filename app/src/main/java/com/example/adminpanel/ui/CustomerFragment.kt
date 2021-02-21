package com.example.adminpanel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adminpanel.R
import com.example.adminpanel.adapter.CustomerAdapter
import com.example.adminpanel.data.CustomerViewModel
import com.example.adminpanel.databinding.FragmentCustomerBinding

class CustomerFragment : Fragment() {

    private lateinit var binding: FragmentCustomerBinding
    private val customerViewModel by lazy {
        ViewModelProvider(this).get(CustomerViewModel::class.java)
    }

    private val customerAdapter by lazy {
        CustomerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer, container, false)
        binding.buttonLoadCustomers.setOnClickListener {
            customerViewModel.getCustomers()
        }
        binding.lifecycleOwner = this
        customerViewModel.allCustomersResponse.observe(viewLifecycleOwner, {
            customerAdapter.submitList(it)
        })
        binding.customerRecyclerView.apply {
            adapter = customerAdapter
        }
        return binding.root
    }
}
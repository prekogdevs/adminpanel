package com.example.adminpanel.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminpanel.api.factory.RetrofitServiceFactory
import com.example.adminpanel.api.model.Customer
import kotlinx.coroutines.launch

class CustomerViewModel : ViewModel() {
    private val _allCustomersResponse = MutableLiveData<List<Customer>>()
    val allCustomersResponse: LiveData<List<Customer>>
        get() = _allCustomersResponse

    private val _newCustomerResponse = MutableLiveData<Customer>()
    val newCustomerResponse: LiveData<Customer>
        get() = _newCustomerResponse

    fun getCustomers() {
        viewModelScope.launch {
            val customers = RetrofitServiceFactory.createCustomerService().getAllCustomers()
            try {
                val result = customers.await()
                _allCustomersResponse.value = result
            } catch (e: Exception) {
                _allCustomersResponse.value = listOf()
                e.printStackTrace()
            }
        }
    }

    fun addCustomer(customer: Customer) {
        viewModelScope.launch {
            val addCustomer =
                RetrofitServiceFactory.createCustomerService().addCustomer(customer)
            try {
                val result = addCustomer.await()
                _newCustomerResponse.value = result
            } catch (e: Exception) {
                _newCustomerResponse.value = null
                e.printStackTrace()
            }
        }
    }
}
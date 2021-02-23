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

    private val _deletedCustomerResponse = MutableLiveData<Customer>()
    val deletedCustomerResponse: LiveData<Customer>
        get() = _deletedCustomerResponse

    private val customerRetrofitService = RetrofitServiceFactory.createCustomerService()

    init {
        getCustomers()
    }

    fun getCustomers() {
        viewModelScope.launch {
            val customers = customerRetrofitService.getAllCustomers()
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
                customerRetrofitService.addCustomer(customer)
            try {
                val result = addCustomer.await()
                _newCustomerResponse.value = result
            } catch (e: Exception) {
                _newCustomerResponse.value = null
                e.printStackTrace()
            }
        }
    }

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            val deletedCustomer =
                customerRetrofitService.deleteCustomer(customer.id)
            try {
                val result = deletedCustomer.await()
                _deletedCustomerResponse.value = result
            } catch (e: Exception) {
                _deletedCustomerResponse.value = null
                e.printStackTrace()
            }
        }
    }
}
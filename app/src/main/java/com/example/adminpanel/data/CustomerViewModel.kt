package com.example.adminpanel.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminpanel.api.CustomerServiceFactory
import com.example.adminpanel.api.model.Customer
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class CustomerViewModel : ViewModel() {
    private val _allCustomersResponse = MutableLiveData<List<Customer>>()
    val allCustomersResponse: LiveData<List<Customer>>
        get() = _allCustomersResponse

    private val _newCustomerResponse = MutableLiveData<Customer>()
    val newCustomerResponse: LiveData<Customer>
        get() = _newCustomerResponse

    fun getCustomers() {
        viewModelScope.launch {
            val customers = CustomerServiceFactory.createCustomerService().getAllcustomers()
            try {
                val result = customers.await()
                _allCustomersResponse.value = result
            } catch (e: Exception) {
                _allCustomersResponse.value = listOf()
                e.printStackTrace()
            }
        }
    }

    fun addCustomer(customer: Customer, avatar: File) {
        val avatarBody =
            MultipartBody.Part.createFormData("avatar", avatar.name, avatar.asRequestBody())
        viewModelScope.launch {
            val addCustomer =
                CustomerServiceFactory.createCustomerService().addCustomer(avatarBody, customer)
            try {
                val result = addCustomer.await()
                _newCustomerResponse.value = result
            } catch (e: Exception) {
                _newCustomerResponse.value = Customer("DUMMY", "DUMMY", "DUMMY", "DUMMY")
                e.printStackTrace()
            }
        }
    }
}
package com.example.adminpanel.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminpanel.api.CustomerApi
import com.example.adminpanel.api.model.Customer
import kotlinx.coroutines.launch
import java.lang.Exception

class CustomerViewModel : ViewModel() {
    private val _response = MutableLiveData<List<Customer>>()
    val response: LiveData<List<Customer>>
        get() = _response

    fun getCustomers() {
        viewModelScope.launch {
            val customers = CustomerApi.retrofitService.getAllcustomers()
            try {
                val result = customers.await()
                _response.value = result
            } catch (e: Exception) {
                _response.value = listOf()
                e.printStackTrace()
            }
        }
    }
}
package com.example.adminpanel.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminpanel.api.factory.RetrofitServiceFactory
import com.example.adminpanel.api.model.Admin
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val _adminResponse = MutableLiveData<Admin>()
    val adminResponse: LiveData<Admin>
        get() = _adminResponse

    fun login(admin: Admin) {
        viewModelScope.launch {
            val customers = RetrofitServiceFactory.createAdminService().login(admin)
            try {
                val result = customers.await()
                _adminResponse.value = result
            } catch (e: Exception) {
                _adminResponse.value = null
                e.printStackTrace()
            }
        }
    }
}
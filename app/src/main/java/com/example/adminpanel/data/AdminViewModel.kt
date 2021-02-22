package com.example.adminpanel.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminpanel.api.factory.RetrofitServiceFactory
import com.example.adminpanel.api.model.Admin
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val _adminLoginResult = MutableLiveData<Boolean>()
    val adminLoginResult: LiveData<Boolean>
        get() = _adminLoginResult

    fun login(admin: Admin) {
        viewModelScope.launch {
            val loginResult = RetrofitServiceFactory.createAdminService().login(admin)
            try {
                val result = loginResult.await()
                _adminLoginResult.value = result
            } catch (e: Exception) {
                _adminLoginResult.value = null
                e.printStackTrace()
            }
        }
    }
}
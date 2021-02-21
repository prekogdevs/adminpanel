package com.example.adminpanel.api.service

import com.example.adminpanel.api.model.Customer
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Customer Service Endpoints
interface CustomerService {
    @GET("customer/all")
    fun getAllCustomers(): Deferred<List<Customer>>

    @POST("customer/register")
    fun addCustomer(
        @Body customer: Customer
    ): Deferred<Customer>
}


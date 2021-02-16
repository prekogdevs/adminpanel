package com.example.adminpanel.api

import com.example.adminpanel.api.model.Customer
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Service Endpoints
interface CustomerService {
    @GET("all")
    fun getAllCustomers(): Deferred<List<Customer>>

    @POST("register")
    fun addCustomer(
        @Body customer: Customer
    ): Deferred<Customer>
}


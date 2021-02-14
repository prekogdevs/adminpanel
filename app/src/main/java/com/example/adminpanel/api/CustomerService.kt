package com.example.adminpanel.api

import com.example.adminpanel.api.model.Customer
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// Service Endpoints
interface CustomerService {
    @GET("customer")
    fun getAllcustomers(): Deferred<List<Customer>>

    @Multipart
    @POST("customer")
    fun addCustomer(
        @Part avatar: MultipartBody.Part,
        @Part("new_customer") customer: Customer
    ): Deferred<Customer>
}


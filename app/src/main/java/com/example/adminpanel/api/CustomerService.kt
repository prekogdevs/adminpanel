package com.example.adminpanel.api

import com.example.adminpanel.api.model.Customer
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import retrofit2.http.*

// Service Endpoints
interface CustomerService {
    @GET("all")
    fun getAllcustomers(): Deferred<List<Customer>>

    // TODO
    @Multipart
    @POST("register")
    fun addCustomer(
        @Part avatar: MultipartBody.Part,
        @Part("new_customer") customer: Customer
    ): Deferred<Customer>

    @POST("register")
    fun addCustomerWithoutAvatar(
        @Body customer: Customer
    ): Deferred<Customer>
}


package com.example.adminpanel.api

import com.example.adminpanel.api.model.Customer
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://10.0.2.2:8080/"
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit =
    Retrofit
        .Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

interface CustomerService {
    @GET("customer")
    fun getAllcustomers(): Deferred<List<Customer>>

    @GET("customer/{id}")
    fun getCustomerById(id: String): Deferred<List<Customer>>
}


object CustomerApi {
    val retrofitService: CustomerService by lazy {
        retrofit.create(CustomerService::class.java)
    }
}



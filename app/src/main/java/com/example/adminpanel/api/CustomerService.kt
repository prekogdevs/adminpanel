package com.example.adminpanel.api

import com.example.adminpanel.api.model.Customer
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val BASE_URL = "http://10.0.2.2:8080/"
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val httpLoggingInterceptor = createHTTPLoggingInterceptor()
private val okHTTPClientBuilder = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
private val retrofit =
    Retrofit
        .Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHTTPClientBuilder.build())
        .baseUrl(BASE_URL)
        .build()

fun createHTTPLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
}

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


object CustomerApi {
    val retrofitService: CustomerService by lazy {
        retrofit.create(CustomerService::class.java)
    }
}



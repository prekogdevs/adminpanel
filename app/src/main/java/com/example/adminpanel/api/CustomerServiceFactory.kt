package com.example.adminpanel.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CustomerServiceFactory {
    private const val API_BASE_URL = "http://10.0.2.2:8080/customer/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val httpLoggingInterceptor = createHTTPLoggingInterceptor()
    private val okHTTPClientBuilder = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)

    private fun createHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    fun createCustomerService(): CustomerService {
        val retrofit =
            Retrofit
                .Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHTTPClientBuilder.build())
                .baseUrl(API_BASE_URL)
                .build()

        return retrofit.create(CustomerService::class.java)
    }

}
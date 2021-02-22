package com.example.adminpanel.api.service

import com.example.adminpanel.api.model.Admin
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

// Admin Service Endpoints
interface AdminService {
    @POST("admin/login")
    fun login(
        @Body admin: Admin
    ): Deferred<Boolean>
}


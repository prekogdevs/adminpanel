package com.example.adminpanel.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatarUrl: String
) : Parcelable
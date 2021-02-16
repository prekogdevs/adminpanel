package com.example.adminpanel.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Customer(
    val name: String = "",
    val email: String = "",
    val avatar: String = ""
) : Parcelable {
    var id: Long = 0
}
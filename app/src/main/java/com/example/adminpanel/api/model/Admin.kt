package com.example.adminpanel.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Admin(
    val username: String = "",
    val password: String = ""
) : Parcelable {
    var id: Long = 0
}
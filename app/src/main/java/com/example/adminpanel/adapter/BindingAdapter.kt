package com.example.adminpanel.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.adminpanel.R

@BindingAdapter("customer_avatar")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        val decodedString = Base64.decode(url, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        Glide.with(view.context).load(bitmap).into(view)
    }
    else {
        // if customer has no picture
        Glide.with(view.context).load(R.drawable.ic_launcher_background).into(view)
    }
}
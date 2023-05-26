package com.dayushman.galleryapp.utils

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.MainThread
import com.bumptech.glide.Glide
import com.dayushman.galleryapp.R

@MainThread
fun ImageView.loadGlide(
    uri: Uri?,
    @DrawableRes placeHolderRes: Int = R.drawable.ic_placeholder,
    @DrawableRes errorHolderRes: Int = placeHolderRes,
) {
    kotlin.runCatching {
        Glide.with(this).load(uri)
            .placeholder(placeHolderRes)
            .error(errorHolderRes)
            .into(this)
    }
}

@MainThread
fun ImageView.loadGlideWithOutTransform(
    uri: Uri?,
    @DrawableRes placeHolderRes: Int = R.drawable.ic_placeholder,
    @DrawableRes errorHolderRes: Int = placeHolderRes,
) {
    kotlin.runCatching {
        Glide.with(this).load(uri)
            .placeholder(placeHolderRes)
            .dontTransform()
            .error(errorHolderRes)
            .into(this)
    }
}
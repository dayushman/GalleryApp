package com.dayushman.galleryapp.features.gallery.data.model

import android.net.Uri

data class LocalImageData(
    val id : Long,
    val displayName : String,
    val size : Int,
    val height : Int,
    val contentUri : Uri,
)



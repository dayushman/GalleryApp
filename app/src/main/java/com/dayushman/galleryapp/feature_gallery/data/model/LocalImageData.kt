package com.dayushman.galleryapp.feature_gallery.data.model

import android.net.Uri

data class LocalImageData(
    val id : Long,
    val displayName : String,
    val size : Int,
    val height : Int,
    val contentUri : Uri,
)



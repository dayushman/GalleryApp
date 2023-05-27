package com.dayushman.galleryapp.feature_gallery.domain.repository

import com.dayushman.galleryapp.feature_gallery.data.model.LocalImageData

interface GalleryRepo {
    suspend fun getImages(searchString : String?,startIndex : Int,endIndex : Int) : List<LocalImageData>
}
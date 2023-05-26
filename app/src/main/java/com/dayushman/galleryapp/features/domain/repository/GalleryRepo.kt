package com.dayushman.galleryapp.features.domain.repository

import com.dayushman.galleryapp.features.data.model.LocalImageData
import com.dayushman.galleryapp.utils.Result
import com.dayushman.galleryapp.utils.ResultUIModel

interface GalleryRepo {
    suspend fun getImages(startIndex : Int,endIndex : Int) : List<LocalImageData>
}
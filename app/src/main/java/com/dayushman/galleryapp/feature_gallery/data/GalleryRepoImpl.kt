package com.dayushman.galleryapp.feature_gallery.data

import com.dayushman.galleryapp.feature_gallery.data.local.LocalImagesService
import com.dayushman.galleryapp.feature_gallery.data.model.LocalImageData
import com.dayushman.galleryapp.feature_gallery.domain.repository.GalleryRepo
import javax.inject.Inject

class GalleryRepoImpl @Inject constructor(private val localImagesService: LocalImagesService) : GalleryRepo {
    override suspend fun getImages(searchString: String?, startIndex: Int, endIndex: Int): List<LocalImageData> {
        return localImagesService.getImages(searchString, startIndex, endIndex)
    }
}
package com.dayushman.galleryapp.features.data

import android.content.ContentResolver
import android.content.ContentUris
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import com.dayushman.galleryapp.features.data.model.LocalImageData
import com.dayushman.galleryapp.features.domain.repository.GalleryRepo
import javax.inject.Inject

class GalleryRepoImpl  @Inject constructor(private val contentResolver: ContentResolver): GalleryRepo {
    override suspend fun getImages(startIndex: Int, endIndex: Int): List<LocalImageData> {
        val localImageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val projections = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT,
        )
        val images = mutableListOf<LocalImageData>()
        val contentCursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) contentResolver.query(
            localImageUri,
            projections, Bundle().apply {
                putInt(ContentResolver.QUERY_ARG_LIMIT, endIndex-startIndex)
                putInt(ContentResolver.QUERY_ARG_OFFSET,startIndex)
            }, null
        ) else contentResolver.query(
            localImageUri,
            projections, null, null, "${MediaStore.Images.Media.DISPLAY_NAME} ASC  LIMIT ${endIndex-startIndex} OFFSET $startIndex"
        )

         contentCursor?.use { cursor ->
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val widthCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
            val heightCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idCol)
                val name = cursor.getString(nameCol)
                val width = cursor.getInt(widthCol)
                val height = cursor.getInt(heightCol)
                val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                images.add(LocalImageData(id, name, width, height, contentUri))
            }
            return images.toList()
        }?: return emptyList()
    }
}
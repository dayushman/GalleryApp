package com.dayushman.galleryapp.utils

import com.dayushman.galleryapp.features.data.model.LocalImageData

sealed class ResultUIModel<out T : Any> {

    companion object {
        val ERROR_NONE = Error(Exception())
    }

    data class Success<out T : Any>(val data: List<LocalImageData>) : ResultUIModel<T>()

    data class Loading<out T : Any>(val loadingData: T? = null) : ResultUIModel<T>()

    data class Error(val error: Throwable) : ResultUIModel<Nothing>()
}

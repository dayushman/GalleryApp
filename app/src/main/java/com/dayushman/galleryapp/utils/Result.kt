package com.dayushman.galleryapp.utils

sealed class Result<out T : Any?> {

    data class Success<out T : Any>(val data: T) : Result<T>()

    data class Error(
        val error: Throwable,
        val message: String? = null,
        val responseCode: Int = 0,
        val rawErrorResponse: String? = null,
        val status: String? = null
    ) : Result<Nothing>()
}

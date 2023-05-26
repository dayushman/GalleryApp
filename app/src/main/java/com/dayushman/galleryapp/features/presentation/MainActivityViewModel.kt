package com.dayushman.galleryapp.features.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dayushman.galleryapp.features.domain.repository.GalleryPagingSource
import com.dayushman.galleryapp.features.domain.repository.GalleryPagingSource.Companion.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(private val galleryPagingSource: GalleryPagingSource) : ViewModel() {


    fun getLocalImagesPagingDataFlow() =  Pager(PagingConfig(pageSize = PAGE_SIZE)){galleryPagingSource}.flow.cachedIn(viewModelScope)
}
package com.dayushman.galleryapp.feature_gallery.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dayushman.galleryapp.feature_gallery.data.local.GalleryPagingSource
import com.dayushman.galleryapp.feature_gallery.data.local.GalleryPagingSource.Companion.PAGE_SIZE
import com.dayushman.galleryapp.feature_gallery.domain.repository.GalleryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(private val galleryRepo: GalleryRepo)  : ViewModel() {


    private var searchString = MutableLiveData("")
    val flpw =  searchString.switchMap {
        Pager(PagingConfig(pageSize = PAGE_SIZE)){ GalleryPagingSource(galleryRepo,searchString.value) }.flow.cachedIn(viewModelScope).asLiveData()
    }

    fun searchImages(query : String?){
        searchString.value = query
    }
}
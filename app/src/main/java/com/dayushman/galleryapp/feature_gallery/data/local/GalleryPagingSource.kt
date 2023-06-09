package com.dayushman.galleryapp.feature_gallery.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dayushman.galleryapp.feature_gallery.data.model.LocalImageData
import com.dayushman.galleryapp.feature_gallery.domain.repository.GalleryRepo
import java.io.IOException

class GalleryPagingSource constructor(private val galleryRepo: GalleryRepo, private val searchString: String?) :
    PagingSource<Int, LocalImageData>() {
    companion object {
        const val DEFAULT_PAGE_INDEX = 0
        const val PAGE_SIZE = 60
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocalImageData> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = galleryRepo.getImages(searchString, page * PAGE_SIZE, (page + 1).times(PAGE_SIZE))

            LoadResult.Page(
                response, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: SecurityException) {
            return LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, LocalImageData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
package com.dayushman.galleryapp.di

import com.dayushman.galleryapp.feature_gallery.data.GalleryRepoImpl
import com.dayushman.galleryapp.feature_gallery.domain.repository.GalleryRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ActivityModule {
    @Binds
    abstract fun provideGalleryRepo(galleryRepoImpl : GalleryRepoImpl) : GalleryRepo

}
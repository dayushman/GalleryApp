package com.dayushman.galleryapp.features.gallery.di

import com.dayushman.galleryapp.features.gallery.data.GalleryRepoImpl
import com.dayushman.galleryapp.features.gallery.domain.repository.GalleryRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ActivityModule {
    @Binds
    abstract fun provideGalleryRepo(galleryRepoImpl: GalleryRepoImpl) : GalleryRepo

}
package com.dayushman.galleryapp.features.gallery.di

import android.content.ContentResolver
import android.content.Context
import com.dayushman.galleryapp.features.gallery.data.GalleryRepoImpl
import com.dayushman.galleryapp.features.gallery.domain.repository.GalleryRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    companion object{
        @Singleton
        @Provides
        fun provideContentResolver(@ApplicationContext context: Context) : ContentResolver{return context.applicationContext.contentResolver}

    }
}
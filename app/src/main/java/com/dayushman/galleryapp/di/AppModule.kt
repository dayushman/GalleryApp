package com.dayushman.galleryapp.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
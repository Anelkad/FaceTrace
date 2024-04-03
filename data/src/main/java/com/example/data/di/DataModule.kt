package com.example.data.di

import com.example.data.local.MediaFacade
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    @Named("MEDIA_FACADE")
    fun provideMediaFacade(): MediaFacade = MediaFacade()
}